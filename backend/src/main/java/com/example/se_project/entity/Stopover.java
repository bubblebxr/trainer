package com.example.se_project.entity;

<<<<<<< HEAD
import java.sql.Time;
=======
>>>>>>> 741074827a3d28fb08a6fbb061711a039014b4e6
import java.util.Date;

public class Stopover {
    private String stationId;

    private String stationName;

    private Date arriveTime;

    private Date leaveTime;

<<<<<<< HEAD
    private Time duration;
=======
    private long duration;
>>>>>>> 741074827a3d28fb08a6fbb061711a039014b4e6

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

<<<<<<< HEAD
    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
=======
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
>>>>>>> 741074827a3d28fb08a6fbb061711a039014b4e6
        this.duration = duration;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }
}
