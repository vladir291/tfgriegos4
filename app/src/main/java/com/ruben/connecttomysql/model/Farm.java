package com.ruben.connecttomysql.model;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by ruben on 19/12/2016.
 */
@SuppressWarnings("serial")
public class Farm implements Serializable{
    private Integer id;
    private String name;
    private String location;


    public Farm(Integer id,String name, String location){
        this.id = id;
        this.name = name;
        this.location = location;

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


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString(){
        String s= getName();
        return s;
    }
}
