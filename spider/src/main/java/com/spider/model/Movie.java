package com.spider.model;

/**
 * Movie
 *
 * @author zhengchaohui
 * @date 2020/12/11 14:55
 */
public class Movie {

    private String id; //电影的id
    private String  directors;//导演
    private String title;//标题
    private String cover;//封面
    private String rate;//评分
    private String casts;//演员


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

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


