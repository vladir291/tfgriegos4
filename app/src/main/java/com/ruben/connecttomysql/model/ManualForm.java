package com.ruben.connecttomysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class ManualForm implements Serializable{
    private String name;
    private Timestamp cancelMoment;
    private Timestamp startDate;
    private Integer duration;


    public ManualForm(String name, Timestamp startDate, Integer duration){
        super();
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getCancelMoment() {
        return cancelMoment;
    }

    public void setCancelMoment(Timestamp cancelMoment) {
        this.cancelMoment = cancelMoment;
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
