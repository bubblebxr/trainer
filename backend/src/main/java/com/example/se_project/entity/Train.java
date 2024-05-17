package com.example.se_project.entity;

import java.util.Date;
import java.util.List;

public class Train {
    private String trainId;

    private Date date;

    private Stopover startStopover;

    private Stopover arrivalStopover;

    private Date startTime;

    private Date arrivalTime;

    private long duration;

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

    private List<Stopover> stopover;

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

    public Stopover getStartStation() {
        return startStopover;
    }

    public void setStartStation(Stopover startStopover) {
        this.startStopover = startStopover;
    }

    public Stopover getArrivalStation() {
        return arrivalStopover;
    }

    public void setArrivalStation(Stopover arrivalStopover) {
        this.arrivalStopover = arrivalStopover;
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

    public List<Stopover> getStopover() {
        return stopover;
    }

    public void setStopover(List<Stopover> stopover) {
        this.stopover = stopover;
    }
}
