package com.example.se_project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Hotel{
    private Integer id;
    @TableField(value="city")
    private String arrive_station;
    private String name;
    @TableField(value = "rank_score")
    private double rank;
    private Integer stars;
    private Integer likes;
    private Integer messages;
    private String miniprice;
    private String photo;
    private String description;
    private String content;
    private List<Comment> comments;
    private List<Room> rooms;
    //private Map<String, Integer> date_num;

    public String getArrive_station() {
        return arrive_station;
    }

    public void setArrive_station(String arrive_station) {
        this.arrive_station = arrive_station;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getMessages() {
        return messages;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }

    public String getMiniprice() {
        return miniprice;
    }

    public void setMiniprice(String miniprice) {
        this.miniprice = miniprice;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

//    public Map<String, Integer> getDate_num() {
//        return date_num;
//    }

//    public void setDate_num(Map<String, Integer> date_num) {
//        this.date_num = date_num;
//    }

}
