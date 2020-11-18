package com.learn.base;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DrawDemo
 * 画图
 * @author zhengchaohui
 * @date 2020/11/10 18:10
 */
@SuppressWarnings("ALL")
public class DrawDemo
        extends JPanel{
    JFrame frame;
    /**
     * 每个点的间隔
      */
    private static final Integer X_SPACE = 10;

    /**
     * 扩大倍数
     */
    private static final Integer ZOOM = 400;

    /**
     * 偏移
     */
    private static final Integer Y_OFFSET = 100;
    private static final Integer X_OFFSET = 20;

    /**
     * frame大小
     */
    private static final Integer WIDTH = 1550;
    private static final Integer HEIGHT = 600;

    /**
     * 标题
     */
    private static final String TITLE = "最大回撤率";

    /**
     * 位置
     */
    private static final Integer FRAME_X_LOC = 100;
    private static final Integer FRAME_Y_LOC = 100;

    /**
     * 文字大小
     */
    private static final Integer FONT_SIZE = 11;

    /**
     * 多边形颜色
     */
    private static final Color POLYGON_COLOR = new Color(255, 200, 0, 98);

    /**
     * 多边形最低位置
     */
    private static final Integer POLYGON_LOWWER_LOC = 500;

    private List<BigDecimal> rateOfReturn = new ArrayList<>();
    private int origin = 0, dest = 0;

    public DrawDemo(){ };

    public DrawDemo(List<BigDecimal> rateOfReturn, int origin, int dest) {
        this.rateOfReturn = new ArrayList<>(rateOfReturn);
        this.origin = origin;
        this.dest = dest;
    }


    public void go(List<BigDecimal> rateOfReturn, int orgin, int dest) {
        frame = new JFrame();
        DrawDemo drawDemo = new DrawDemo(rateOfReturn, orgin, dest);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(drawDemo);
        frame.setLocation(FRAME_X_LOC, FRAME_Y_LOC);
        frame.setTitle(TITLE);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Font font = new Font(Font.DIALOG, Font.BOLD, FONT_SIZE);
        g.setFont(font);
        int[] x1s = new int[dest - origin + 3];
        int[] y1s = new int[dest - origin + 3];
        int index = 1;
        // TODO 有个bug，如果最后回测率区间的最后一个点就是数组的最后一个点，那么画多面的时候会出问题
        for (int i = 0; i < rateOfReturn.size() - 1; i++) {

            int y1 = this.getHeight() - (rateOfReturn.get(i).multiply(BigDecimal.valueOf(ZOOM)).intValue() + Y_OFFSET);
            int y2 = this.getHeight() - (rateOfReturn.get(i + 1).multiply(BigDecimal.valueOf(ZOOM)).intValue() + Y_OFFSET);
            int x1 = X_SPACE * i + X_OFFSET;
            int x2 = X_SPACE * (i + 1) + X_OFFSET;

            if (i >= origin && i <= dest - 1) {
                x1s[index] = x1;
                y1s[index++] = y1;
                g.setColor(Color.MAGENTA);
            } else {
                if (i == dest) {
                    //
                    x1s[index] = x1;
                    y1s[index++] = y1;
                }
                g.setColor(Color.DARK_GRAY);
            }

            g.drawLine(x1, y1,
                    x2, y2);
            // 最大回撤区间
            if (i == dest) {
                g.setColor(Color.MAGENTA);
            }
            g.drawString(this.getHeight() - y1 + "", x1, y1);
        }
        // 画最后一个点
        int rY1 = this.getHeight() - (rateOfReturn.get(rateOfReturn.size() - 1).multiply(BigDecimal.valueOf(ZOOM)).intValue() + Y_OFFSET);;
        int rX1 = 10 * (rateOfReturn.size() - 1) + X_OFFSET;
        g.drawString(this.getHeight() - rY1 + "", rX1, rY1);


        // 画多边形
        g.setColor(POLYGON_COLOR);
        // 多面第一个点
        x1s[0] = x1s[1];
        y1s[0] = POLYGON_LOWWER_LOC;
        // 多面最后一个点
        x1s[x1s.length - 1] = x1s[x1s.length - 2];
        y1s[y1s.length - 1] = POLYGON_LOWWER_LOC;
        g.fillPolygon(x1s, y1s, x1s.length);
    }
}
