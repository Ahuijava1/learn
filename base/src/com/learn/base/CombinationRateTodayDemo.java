package com.learn.base;

import com.learn.base.po.OrderRecord;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CombinationRateTodayDemo
 * 组合收益模拟计算（单个组合策略）
 *
 * @author zhengchaohui
 * @date 2020/11/17 9:08
 */
public class CombinationRateTodayDemo {
    /**
     * 订单记录
     */
    private static List<OrderRecord> orders = new ArrayList<>();
    /**
     * 今天收市价格
     */
    private static Map<String, BigDecimal> todayPrices = new HashMap<>(16);
    /**
     * 昨天收市价
     */
    private static Map<String, BigDecimal> yesterdayPrices = new HashMap<>(16);


    // 证券市值=现价*股数，策略组合证券市值=∑证券市值

    static {
        //
        Calendar calendar = Calendar.getInstance();
        // 查询时可以先排好序
        calendar.add(Calendar.DATE, -1);
        orders.add(new OrderRecord("B", "00700", new BigDecimal("560.50"), 1000, calendar.getTime(), new BigDecimal(0), new BigDecimal(50)));
        calendar.add(Calendar.DATE, 1);
        orders.add(new OrderRecord("S", "00700", new BigDecimal("560.50"), 500, calendar.getTime(), new BigDecimal(50), new BigDecimal(25)));
        calendar.add(Calendar.MINUTE, 1);
        orders.add(new OrderRecord("S", "00700", new BigDecimal("562.50"), 200, calendar.getTime(), new BigDecimal(25), new BigDecimal(13)));
        calendar.add(Calendar.MINUTE, 1);
        orders.add(new OrderRecord("S", "00700", new BigDecimal("558.50"), 300, calendar.getTime(), new BigDecimal(13), new BigDecimal(0)));
        calendar.add(Calendar.MINUTE, 1);
        orders.add(new OrderRecord("B", "00700", new BigDecimal("561.50"), 500, calendar.getTime(), new BigDecimal(0), new BigDecimal(25)));
        calendar.add(Calendar.MINUTE, 1);
        orders.add(new OrderRecord("B", "00500", new BigDecimal("563.50"), 300, calendar.getTime(), new BigDecimal(0), new BigDecimal(13)));
        calendar.add(Calendar.MINUTE, 1);
        orders.add(new OrderRecord("S", "00500", new BigDecimal("561.50"), 200, calendar.getTime(), new BigDecimal(13), new BigDecimal(5)));
        todayPrices.put("00700", new BigDecimal(562));
        todayPrices.put("00500", new BigDecimal(560));
        yesterdayPrices.put("00700", new BigDecimal(560.00));
        yesterdayPrices.put("00500", new BigDecimal(560.00));


        // 按股票分组，所有记录，其实还要根据组合策略分组
        Map<String, List<OrderRecord>> stockGroup = orders.stream().collect(Collectors.groupingBy(OrderRecord::getProductCode));

        // 该策略每支股票的所有记录
        stockGroup.forEach((k, v) -> {
            // 卖出的价值大于买入的价值（不算手续费的情况）
            // ∑（卖出股数1*卖出成交价1 - 手续费1）- ∑（买入股数2*买入成本价2 + 手续费2）

        });

        // 按股票分组（并且只保留今天的数据，向mongo查询的时候，如果只是计算收益率，那么倒是可以只取今天的数据。
        // 可是计算胜率的时候，则必须要把所有数据都拉取。因此，做过滤是有必要的）
        Map<String, List<OrderRecord>> stockGroupToday = orders.stream().filter(s -> isToday(s.getCreateTime())).collect(Collectors.groupingBy(OrderRecord::getProductCode));

        final BigDecimal[] stockTotal = {new BigDecimal("0.0"), new BigDecimal("0.0")};

        // 循环策略里面的每一支股票今天的调仓记录为v
        stockGroupToday.forEach((k, v) -> {
            // 每天组合盈亏金额=∑当前持仓所有股票的盈亏金额（包括今天已清仓）
            stockTotal[0] = stockTotal[0].add(countSingleStock("HK", k, v));

            // 计算该策略今日浮动收益大于0的股票数
            // 获取现价(接口调用)、综合成本价（数据库）计算
            // （（现价-综合成本价）/综合成本价）*100%，精确至小数点后2位，百分比，向下取整；

            // 该策略总市值
            // ∑k对应的股票的综合成本价*股数 （数据库）


        });

        // 最后还得除该策略总市值

        System.out.println(stockTotal[0]);
    }

    /**
     * 判断是否为今天
     *
     * @param date 判断的日期
     * @return boolean
     */
    private static boolean isToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        // 今天的年月日
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.setTime(date);
        return year == calendar.get(Calendar.YEAR)
                && month == calendar.get(Calendar.MONTH)
                && day == calendar.get(Calendar.DATE);
    }

    /**
     * 计算单只股票今日盈亏金额
     *
     * @param ts          市场
     * @param productCode 股票代码
     * @param orderRecords 今日调仓记录
     */
    private static BigDecimal countSingleStock(String ts, String productCode, List<OrderRecord> orderRecords) {

        /*
         * 当前各股的持仓数和昨天各股的持仓数有记录吗？没有的话可能要自己算了
         * 当前的持仓数保存了数据库了，昨天的也有了
         */

        // 每只股票今日盈亏金额=(今日当前单股票市值-昨日收市单股票市值)+(今日单股票卖出成交额-今日单股票买入成交额）
        // 今日当前单股票市值=当前价格*当前持仓股数
        BigDecimal todayValue = todayPrices.get(productCode).multiply(new BigDecimal(2100));
        // 昨日收市单股票市值=昨日收盘价*昨日收盘时持仓数量
        BigDecimal yesterdayValue = yesterdayPrices.get(productCode).multiply(new BigDecimal(100));
        Map<String, BigDecimal> map = getTodayBuyAndSellTotal(orderRecords);
        // 今日单股票卖出成交额=∑卖出股数*参考成交金额
        BigDecimal todaySellTotal = map.get("todaySellTotal");
        // 今日单股票买入成交额=∑买入股数*参考成交金额
        BigDecimal todayBuyTotal = map.get("todayBuyTotal");
        BigDecimal singleStock = todayValue.subtract(yesterdayValue).add(todaySellTotal).subtract(todayBuyTotal);

        System.out.println(ts + productCode + " 今日盈亏金额:" + singleStock);
        System.out.println(map.get("historyStockProfit") + ", " + map.get("historyStock"));
        return singleStock;
    }

    /**
     * 计算单只股票今日买入卖出成交额
     *
     * @return map
     * @param orderRecords
     */
    private static Map<String, BigDecimal> getTodayBuyAndSellTotal(List<OrderRecord> orderRecords) {
        // 今日单股票卖出成交额=∑卖出股数*参考成交金额
        BigDecimal todaySellTotal = new BigDecimal("0.0");
        // 今日单股票买入成交额=∑买入股数*参考成交金额
        BigDecimal todayBuyTotal = new BigDecimal("0.0");
        int historyStockProfit = 0;
        int historyStock = 0;
        for (OrderRecord orderRecord : orderRecords) {
            // 选择今天*
            if (isToday(orderRecord.getCreateTime())) {
                // 买
                if ("S".equals(orderRecord.getBsFlag())) {
                    todaySellTotal = todaySellTotal.add(orderRecord.getPrice().multiply(BigDecimal.valueOf(orderRecord.getQty())));
                } else {
                    // 卖
                    todayBuyTotal = todayBuyTotal.add(orderRecord.getPrice().multiply(BigDecimal.valueOf(orderRecord.getQty())));
                }
            }

            // 查看今日是否有调仓为0的
            if (orderRecord.getAfterHold().equals(BigDecimal.ZERO)) {
                // 找离当前位置到最近的新买入的所有数据
                // 模拟数据
                List<OrderRecord> list = findHistoryOrderRecord(orderRecord.getProductCode(), "HK", orderRecord.getCreateTime());
                // 根据这些数据判断是否获利
                if(getProfit(list).compareTo(BigDecimal.ZERO) > 0) {
                    // 策略历史了结获利+1
                    historyStockProfit++;
                }

                // 历史了结股票数+1
                historyStock++;
            }
        }

        Map<String, BigDecimal> map = new HashMap<>(4);
        map.put("todaySellTotal", todaySellTotal);
        map.put("todayBuyTotal", todayBuyTotal);
        map.put("historyStockProfit", BigDecimal.valueOf(historyStockProfit));
        map.put("historyStock", BigDecimal.valueOf(historyStock));
        return map;
    }

    /**
     * 计算该股票清仓后是否获利
     * @param list 历史数据
     * @return BigDecimal
     */
    private static BigDecimal getProfit(List<OrderRecord> list) {
        // 今日单股票卖出成交额=∑卖出股数*参考成交金额
        BigDecimal sellTotal = new BigDecimal("0.0");
        // 今日单股票买入成交额=∑买入股数*参考成交金额
        BigDecimal buyTotal = new BigDecimal("0.0");
        for (OrderRecord orderRecord : list) {
            if ("S".equals(orderRecord.getBsFlag())) {
                sellTotal = sellTotal.add(orderRecord.getPrice().multiply(BigDecimal.valueOf(orderRecord.getQty())));
            } else {
                // 卖
                buyTotal = buyTotal.add(orderRecord.getPrice().multiply(BigDecimal.valueOf(orderRecord.getQty())));
            }
        }

        return sellTotal.subtract(buyTotal);
    }

    /**
     * 模拟查询当前位置到最近的新买入的所有数据
     * @param productCode 股票代码
     * @param market 市场
     * @param createTime 时间
     * @return List<OrderRecord>
     */
    private static List<OrderRecord> findHistoryOrderRecord(String productCode, String market, Date createTime) {
        List<OrderRecord> ordersDemo = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        // 查询时可以先排好序
        calendar.add(Calendar.DATE, -1);
        ordersDemo.add(new OrderRecord("B", productCode, new BigDecimal("5"), 1000, calendar.getTime(), new BigDecimal(0), new BigDecimal(50)));
        calendar.add(Calendar.DATE, 1);
        ordersDemo.add(new OrderRecord("S", productCode, new BigDecimal("6"), 500, calendar.getTime(), new BigDecimal(50), new BigDecimal(25)));
        calendar.add(Calendar.MINUTE, 1);
        ordersDemo.add(new OrderRecord("S", productCode, new BigDecimal("6"), 200, calendar.getTime(), new BigDecimal(25), new BigDecimal(13)));
        calendar.add(Calendar.MINUTE, 1);
        ordersDemo.add(new OrderRecord("S", productCode, new BigDecimal("6"), 300, calendar.getTime(), new BigDecimal(13), new BigDecimal(0)));
        return ordersDemo;
    }

    public static void main(String[] args) {

    }
}
