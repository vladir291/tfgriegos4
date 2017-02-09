package com.ruben.connecttomysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class Manual extends Irrigation implements Serializable{
    private Timestamp startDate;
    private Integer duration;


    public Manual(Integer id, String name, Timestamp cancelMoment, Timestamp startDate, Integer duration){
        super();
        this.setId(id);
        this.setName(name);
        this.setCancelMoment(cancelMoment);
        this.startDate = startDate;
        this.duration = duration;

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
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
