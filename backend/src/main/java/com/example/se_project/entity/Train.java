package com.example.se_project.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;

@TableName("trains")
public class Train {
    private String trainId;

    private Date date;

    private Station startStation;

    private Station arrivalStation;

    private Date startTime;

    private Date arrivalTime;

    private long duration;
    // 0高铁 1火车
    private Integer type;

    private int businessSeatSurplus;

    private double businessSeatPrice;

    private int firstClassSeatSurplus;

    private double firstClassSeatPrice;

    private int secondClassSeatSurplus;

    private double secondClassSeatPrice;

    private int softSleeperSurplus;

    private double softSleeperPrice;

    private int hardSleeperSurplus;

    private double hardSleeperPrice;
    private int hardSeatplus;
    private double hardSeatPrice;

    private List<Station> stopover;

    public int getHardSeatplus() {
        return hardSeatplus;
    }

    public void setHardSeatplus(int hardSeatplus) {
        this.hardSeatplus = hardSeatplus;
    }

    public double getHardSeatPrice() {
        return hardSeatPrice;
    }

    public void setHardSeatPrice(double hardSeatPrice) {
        this.hardSeatPrice = hardSeatPrice;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getBusinessSeatSurplus() {
        return businessSeatSurplus;
    }

    public void setBusinessSeatSurplus(int businessSeatSurplus) {
        this.businessSeatSurplus = businessSeatSurplus;
    }

    public double getBusinessSeatPrice() {
        return businessSeatPrice;
    }

    public void setBusinessSeatPrice(double businessSeatPrice) {
        this.businessSeatPrice = businessSeatPrice;
    }

    public int getFirstClassSeatSurplus() {
        return firstClassSeatSurplus;
    }

    public void setFirstClassSeatSurplus(int firstClassSeatSurplus) {
        this.firstClassSeatSurplus = firstClassSeatSurplus;
    }

    public double getFirstClassSeatPrice() {
        return firstClassSeatPrice;
    }

    public void setFirstClassSeatPrice(double firstClassSeatPrice) {
        this.firstClassSeatPrice = firstClassSeatPrice;
    }

    public int getSecondClassSeatSurplus() {
        return secondClassSeatSurplus;
    }

    public void setSecondClassSeatSurplus(int secondClassSeatSurplus) {
        this.secondClassSeatSurplus = secondClassSeatSurplus;
    }

    public double getSecondClassSeatPrice() {
        return secondClassSeatPrice;
    }

    public void setSecondClassSeatPrice(double secondClassSeatPrice) {
        this.secondClassSeatPrice = secondClassSeatPrice;
    }

    public int getSoftSleeperSurplus() {
        return softSleeperSurplus;
    }

    public void setSoftSleeperSurplus(int softSleeperSurplus) {
        this.softSleeperSurplus = softSleeperSurplus;
    }

    public double getSoftSleeperPrice() {
        return softSleeperPrice;
    }

    public void setSoftSleeperPrice(double softSleeperPrice) {
        this.softSleeperPrice = softSleeperPrice;
    }

    public int getHardSleeperSurplus() {
        return hardSleeperSurplus;
    }

    public void setHardSleeperSurplus(int hardSleeperSurplus) {
        this.hardSleeperSurplus = hardSleeperSurplus;
    }

    public double getHardSleeperPrice() {
        return hardSleeperPrice;
    }

    public void setHardSleeperPrice(double hardSleeperPrice) {
        this.hardSleeperPrice = hardSleeperPrice;
    }

    public List<Station> getStopover() {
        return stopover;
    }

    public void setStopover(List<Station> stopover) {
        this.stopover = stopover;
    }
}