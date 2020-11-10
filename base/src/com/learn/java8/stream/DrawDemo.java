package com.learn.java8.stream;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DrawDemo
 *
 * @author zhengchaohui
 * @date 2020/11/10 18:10
 */
public class DrawDemo
        extends JPanel{
    JFrame frame;
    private List<BigDecimal> rateOfReturn = new ArrayList<>();

    public DrawDemo(){ };
    public DrawDemo(List<BigDecimal> rateOfReturn) {
        this.rateOfReturn = new ArrayList<>(rateOfReturn);
    }


    public void go(List<BigDecimal> rateOfReturn) {
        frame = new JFrame();
        DrawDemo drawDemo = new DrawDemo(rateOfReturn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 500);
        frame.add(drawDemo);
        frame.setTitle("title");
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        int height = this.getHeight();
        int width = this.getWidth();
        //在屏幕中间构建平面直角坐标系
        double x, y;
        g.setColor(Color.blue);
        System.out.println(this.rateOfReturn);

        for (int i = 0; i < rateOfReturn.size(); i++) {
            g.fillRect(i, rateOfReturn.get(i).multiply(BigDecimal.valueOf(100)).intValue(), 1, 1);
        }
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                //遍历平面直角坐标系,如果一个点满足要求就绘制这个点
//                x = (i - width / 2 - 1) / 100.0;
//                y = (height / 2 - j - 1) / 100.0;
//                if (isRight(y, x * x * x, 0.02)) {
//                    g.fillOval(i, j, 2, 2);
//                }
//            }
//        }
    }

    boolean isRight(double num1, double num2, double error) {
        //判断两个数字是否在误差允许范围
        if (Math.abs(num1 - num2) <= error)
            return true;
        return false;
    }
}
