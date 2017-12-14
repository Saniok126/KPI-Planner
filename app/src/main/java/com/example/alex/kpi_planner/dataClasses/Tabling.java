package com.example.alex.kpi_planner.dataClasses;

/**
 * Created by Sonder on 13.12.2017.
 */

public class Tabling {

    private String id;
    private String dayId;
    private String disciplineId;
    private String lessonId;
    private String type;
    private String roomId;
    private String teacher;

    public Tabling(){

    }

    public Tabling(String id, String dayId, String disciplineId, String lessonId, String type) {
        this.id = id;
        this.dayId = dayId;
        this.disciplineId = disciplineId;
        this.lessonId = lessonId;
        this.type = type;
    }

    public boolean isEmpty(){
        return (id == null && dayId == null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    // TODO: constructors


}
