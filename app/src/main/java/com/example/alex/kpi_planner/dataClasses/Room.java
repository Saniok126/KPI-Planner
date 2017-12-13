package com.example.alex.kpi_planner.dataClasses;

/**
 * Created by Sonder on 13.12.2017.
 */

public class Room {

    private String id;
    private String number;
    private String buildingId;

    public boolean isEmpty(){
        return (id == null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }


}
