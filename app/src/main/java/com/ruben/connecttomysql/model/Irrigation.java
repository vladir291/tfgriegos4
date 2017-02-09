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

    public Irrigation(){
        super();
    }


    public Irrigation(Integer id, String name, Timestamp cancelMoment){
        this.id = id;
        this.name = name;
        this.cancelMoment = cancelMoment;

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

    public String toString(){
        String s= getName();
        return s;
    }
}
