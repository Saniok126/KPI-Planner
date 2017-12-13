package com.example.alex.kpi_planner.dataClasses;

/**
 * Created by Sonder on 13.12.2017.
 */

public class Building {

    private String id;
    private String number;
    private String Latitude;
    private String Longitude;

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

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
