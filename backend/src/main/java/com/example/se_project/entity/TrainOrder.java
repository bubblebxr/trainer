package com.example.se_project.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("trainOrders")
public class TrainOrder{
    private String oid;
    private String trainId;
    private String trainDate;
    private String name;
    private String identificaiton;
    private String seatType;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificaiton() {
        return identificaiton;
    }

    public void setIdentificaiton(String identificaiton) {
        this.identificaiton = identificaiton;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}
