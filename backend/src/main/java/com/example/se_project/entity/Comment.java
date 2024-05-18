package com.example.se_project.entity;

public class Comment {
    private Integer hotel_id;
    private String person;
    private String time;
    private String content;
    private Double rank_score;

    public Integer getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Integer hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Double getRank_score() {
        return rank_score;
    }

    public void setRank_score(Double rank_score) {
        this.rank_score = rank_score;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
