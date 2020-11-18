package com.learn.base.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderRecord
 * 订单记录（调仓记录）
 * @author zhengchaohui
 * @date 2020/11/17 9:38
 */
public class OrderRecord {

    /**
     * 买入：B，卖出：
     */
    private String bsFlag;

    private String productCode;

    private BigDecimal price;

    private Integer qty;

    private Date createTime;

    /**
     * 调仓前
     */
    private BigDecimal beforeHold;

    /**
     * 调仓后
     */
    private BigDecimal afterHold;

    public String getBsFlag() {
        return bsFlag;
    }

    public void setBsFlag(String bsFlag) {
        this.bsFlag = bsFlag;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBeforeHold() {
        return beforeHold;
    }

    public void setBeforeHold(BigDecimal beforeHold) {
        this.beforeHold = beforeHold;
    }

    public BigDecimal getAfterHold() {
        return afterHold;
    }

    public void setAfterHold(BigDecimal afterHold) {
        this.afterHold = afterHold;
    }

    public OrderRecord(String bsFlag, String productCode, BigDecimal price, Integer qty, Date createTime) {
        this.bsFlag = bsFlag;
        this.productCode = productCode;
        this.price = price;
        this.qty = qty;
        this.createTime = createTime;
    }

    public OrderRecord(String bsFlag, String productCode, BigDecimal price, Integer qty, Date createTime, BigDecimal beforeHold, BigDecimal afterHold) {
        this.bsFlag = bsFlag;
        this.productCode = productCode;
        this.price = price;
        this.qty = qty;
        this.createTime = createTime;
        this.beforeHold = beforeHold;
        this.afterHold = afterHold;
    }

    @Override
    public String toString() {
        return "OrderRecord{" +
                "bsFlag='" + bsFlag + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", createTime=" + createTime +
                ", beforeHold=" + beforeHold +
                ", afterHold=" + afterHold +
                '}';
    }
}
