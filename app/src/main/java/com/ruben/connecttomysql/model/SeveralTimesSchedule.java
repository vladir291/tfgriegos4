package com.ruben.connecttomysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class SeveralTimesSchedule extends Irrigation implements Serializable{
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer hours;
    private Integer minutes;
    private Integer duration;


    public SeveralTimesSchedule(Integer id, String name, Timestamp cancelMoment, Timestamp startDate, Timestamp endDate, Integer hours, Integer minutes, Integer duration){
        super();
        this.setId(id);
        this.setName(name);
        this.setCancelMoment(cancelMoment);
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.minutes = minutes;
        this.duration = duration;

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String toString(){
        String s= getName();
        return s;
    }
}
