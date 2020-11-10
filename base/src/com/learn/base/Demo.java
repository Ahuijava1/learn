package com.learn.base;

/**
 * RateOfReturnDemo
 *
 * @author zhengchaohui
 * @date 2020/10/15 9:34
 */
public class Demo {

    private final static Integer GROUP_ARMY = 1;
    private final static Integer COUNT = 3;
    public static void main(String[] args) {
        int jituan = 1;
        int jun = 1;
        int shi = 1;
        int lv = 1;
        int tuan = 1;
        // jituan
        for (Integer i = 0; i < GROUP_ARMY; i++) {
            String jituanStr = "第"+ jituan + "集团军";
            System.out.println("------------------" + jituanStr + "------------------");
            jituan++;
            // jun
            for (int j = 0; j < COUNT - 1; j++) {
                String junStr = jituanStr + "第"+ jun + "军";
                System.out.println(junStr + "-------------------------------");
                jun++;
                // shi
                for (int k = 0; k < COUNT; k++) {
                    String shiStr = junStr + "第" + shi +  "师";
                    System.out.println(shiStr );
                    shi++;
                    // lv
                    for (int k1 = 0; k1 < COUNT; k1++) {
                        String lvStr = shiStr + "" + lv + "旅";
                        System.out.println(lvStr);
                        lv++;
                        // 团
                        for (int k2 = 0; k2 < COUNT; k2++) {
                            String tuanStr = lvStr + "" + tuan + "团";
                            System.out.println(tuanStr);
                            tuan++;
                        }
                    }
                    // 师属
                    System.out.println(shiStr + "师属" + tuan + "团（炮团）");
                    tuan++;
                    System.out.println(shiStr + "师属" + tuan + "团（防空团）");
                    tuan++;
                    System.out.println(shiStr + "师属" + tuan + "团（装甲团）");
                    tuan++;
                }
            }
        }
    }
}
