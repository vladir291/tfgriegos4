package com.ruben.connecttomysql.model;

import java.io.Serializable;

/**
 * Created by ruben on 21/12/2016.
 */

public class ClimatologicalProbe implements Serializable {

    private Integer id;
    private Double soilLowerLimit;
    private Double soilUpperLimit;
    private Boolean active;
    private Integer plotId;


    public ClimatologicalProbe(Integer id,Double soilLowerLimit,Double soilUpperLimit,Boolean active, Integer plotId){
        this.id = id;
        this.soilLowerLimit = soilLowerLimit;
        this.soilUpperLimit = soilUpperLimit;
        this.active = active;
        this.plotId = plotId;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlotId() {
        return plotId;
    }

    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    public Double getSoilUpperLimit() {
        return soilUpperLimit;
    }

    public void setSoilUpperLimit(Double soilUpperLimit) {
        this.soilUpperLimit = soilUpperLimit;
    }

    public Double getSoilLowerLimit() {
        return soilLowerLimit;
    }

    public void setSoilLowerLimit(Double soilLowerLimit) {
        this.soilLowerLimit = soilLowerLimit;
    }
    public String toString(){
        String s= getId()+"";
        return s;
    }
}
