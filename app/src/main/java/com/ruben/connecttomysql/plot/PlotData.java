package com.ruben.connecttomysql.plot;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by ruben on 11/01/2017.
 */

public class PlotData implements Serializable {
    private Integer id;
    private Timestamp measuringMoment;
    private Double soilMoisture;
    private Double humidity;
    private Double temperature;
    private Integer plotId;


    public PlotData(Integer id,Timestamp measuringMoment,Double soilMoisture,Double humidity,Double temperature,Integer plotId){
        this.id = id;
        this.measuringMoment = measuringMoment;
        this.soilMoisture = soilMoisture;
        this.humidity = humidity;
        this.temperature = temperature;
        this.plotId = plotId;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(Double soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public Timestamp getMeasuringMoment() {
        return measuringMoment;
    }

    public void setMeasuringMoment(Timestamp measuringMoment) {
        this.measuringMoment = measuringMoment;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getPlotId() {
        return plotId;
    }

    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }
}
