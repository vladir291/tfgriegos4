package com.ruben.connecttomysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class IrrigationMomentDay implements Serializable{

    private Integer id;
    private Timestamp irrigationMoment;
    private Integer duration;
    private Integer idIrrigation;
    private Integer idSeveralTimesDaysSchedule;



    public IrrigationMomentDay(Integer id, Timestamp irrigationMoment, Integer duration, Integer idSeveralTimesDaysSchedule, Integer idIrrigation){
        super();
        this.id = id;
        this.irrigationMoment = irrigationMoment;
        this.idSeveralTimesDaysSchedule = idSeveralTimesDaysSchedule;
        this.duration = duration;
        this.idIrrigation = idIrrigation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getIrrigationMoment() {
        return irrigationMoment;
    }

    public void setIrrigationMoment(Timestamp irrigationMoment) {
        this.irrigationMoment = irrigationMoment;
    }
    public Integer getIdSeveralTimesDaysSchedule() {
        return idSeveralTimesDaysSchedule;
    }

    public void setIdSeveralTimesDaysSchedule(Integer idSeveralTimesDaysSchedule) {
        this.idSeveralTimesDaysSchedule = idSeveralTimesDaysSchedule;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getIdIrrigation() {
        return idIrrigation;
    }

    public void setIdIrrigation(Integer idIrrigation) {
        this.idIrrigation = idIrrigation;
    }

    public String toString(){
        String s= getIrrigationMoment().toString() +" - duración: "+getDuration();
        return s;
    }
}
