package com.ruben.connecttomysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class Irrigation implements Serializable{
    private Integer id;
    private String name;
    private Timestamp cancelMoment;
    private Timestamp startDate;
    private Timestamp endDate;
    private String tipoRiego;

    public Irrigation(){
        super();
    }

    public Irrigation(Integer id, String name, Timestamp cancelMoment, Timestamp startDate, Timestamp endDate, String tipoRiego){
        this.id = id;
        this.name = name;
        this.cancelMoment = cancelMoment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tipoRiego = tipoRiego;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Date getCancelMoment() {
        return cancelMoment;
    }

    public void setCancelMoment(Timestamp cancelMoment) {
        this.cancelMoment = cancelMoment;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getTipoRiego() {
        return tipoRiego;
    }

    public void setTipoRiego(String tipoRiego) {
        this.tipoRiego = tipoRiego;
    }

    public String toString(){
        String s= getName() +" - "+ getTipoRiego();
        return s;
    }
}
