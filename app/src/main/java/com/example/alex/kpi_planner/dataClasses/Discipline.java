package com.example.alex.kpi_planner.dataClasses;

/**
 * Created by Sonder on 13.12.2017.
 */

public class Discipline {

    private String id;
    private String name;
    private String fullName;
    private String number;

    public boolean isEmpty(){
        return (id == null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
