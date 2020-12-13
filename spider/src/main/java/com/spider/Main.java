package com.spider;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import com.spider.model.Movie;
import com.spider.utils.GetJson;

import java.util.List;

/**
 * @author ZhengChaoHui
 * @Date 2020/12/11 22:56
 */
@Slf4j
public class Main {
    public static void main(String[] args) {


        int start;//每页多少条
        int total = 0;//记录数
        int end = 9979;//总共9979条数据
        for (start = 0; start <= 40; start += 20) {
            try {

                String address = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=" + start;

                JSONObject dayLine = new GetJson().getHttpJson(address, 1);

                System.out.println("start:" + start);
                JSONArray json = dayLine.getJSONArray("data");
                List<Movie> list = JSON.parseArray(json.toString(), Movie.class);

                if (start <= end) {
                    System.out.println("已经爬取到底了");
                }
                for (Movie movie : list) {
                    System.out.println(movie);
                }
                total += list.size();
                System.out.println("正在爬取中---共抓取:" + total + "条数据");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
