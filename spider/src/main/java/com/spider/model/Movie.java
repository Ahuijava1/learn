package com.spider.model;

import lombok.Data;

/**
 * Movie
 *
 * @author zhengchaohui
 * @date 2020/12/11 14:55
 */
@Data
public class Movie {

    private String id; //电影的id
    private String  directors;//导演
    private String title;//标题
    private String cover;//封面
    private String rate;//评分
    private String casts;//演员

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", directors='" + directors + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", rate='" + rate + '\'' +
                ", casts='" + casts + '\'' +
                '}';
    }
}


