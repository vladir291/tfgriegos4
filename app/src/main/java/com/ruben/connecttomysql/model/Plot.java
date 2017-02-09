package com.ruben.connecttomysql.model;

import java.io.Serializable;

/**
 * Created by ruben on 20/12/2016.
 */

public class Plot implements Serializable {
    private Integer id;
    private String name;
    private Boolean waterPump;
    private Integer farmId;


    public Plot(Integer id,String name, Boolean waterPump,Integer farmId){
        this.id = id;
        this.name = name;
        this.waterPump = waterPump;
        this.farmId=farmId;

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

    public Boolean getWaterPump() {
        return waterPump;
    }

    public void setWaterPump(Boolean waterPump) {
        this.waterPump = waterPump;
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public String toString(){
        String s= getName();
        return s;
    }
}
