package com.example.alex.kpi_planner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saniok on 11.12.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "groupsDB";

    public static final String TABLE_TEACHERS = "Teacher";
    public static final String TEACHERS_ID = "_id";
    public static final String TEACHERS_NAME = "Name";
    public static final String TEACHERS_SHORT = "Short_name";
    public static final String TEACHERS_DEGREE = "Degree";

    public static final String TABLE_TEACH_DISC_RELATIONS = "Teach_Disc_relations";
    public static final String TDR_ID = "_id";
    public static final String TDR_TEACHERS_ID = "Teacher_id";
    public static final String TDR_DISCIPLINE_ID = "Discipline_id";

    public static final String TABLE_GROUP = "class";
    public static final String GROUP_ID = "_id";
    public static final String GROUP_NAME = "Name";

    public static final String TABLE_DAY = "Day";
    public static final String DAY_ID = "_id";
    public static final String DAY_NUMBER = "Number";
    public static final String DAY_NAME = "Name";
    public static final String DAY_WEEK = "Week";

    public static final String TABLE_TABLING = "Tabling";
    public static final String TABLING_ID = "_id";
    public static final String TABLING_DAY_ID = "Day_id";
    public static final String TABLING_DISC_ID = "Discipline_id";
    public static final String TABLING_LESSON_ID = "Lesson_id";
    public static final String TABLING_ROOM_ID = "Room_id";
    public static final String TABLING_TYPE = "Type";

    public static final String TABLE_GROUP_DISC_RELATIONS = "Group_Disc_relations";
    public static final String GDR_ID = "_id";
    public static final String GDR_GROUP_ID = "Group_id";
    public static final String GDR_DISC_ID = "Discipline_id";

    public static final String TABLE_DISCIPLINE = "DIscipline";
    public static final String DISC_ID = "_id";
    public static final String DISC_NAME = "Name";
    public static final String DISC_FULL_NAME = "Full_name";

    public static final String TABLE_LESSON = "Lesson";
    public static final String LESSON_ID = "_id";
    public static final String LESSON_START_TIME = "Start_time";
    public static final String LESSON_END_TIME = "End_time";

    public static final String TABLE_ROOM = "Room";
    public static final String ROOM_ID = "_id";
    public static final String ROOM_NAME = "Name";
    public static final String ROOM_BUILD_ID = "Building_id";

    public static final String TABLE_BUILDING = "Building";
    public static final String BUILD_ID = "_id";
    public static final String BUILD_NAME = "Name";
    public static final String BUILD_LATITUDE = "Latitude";
    public static final String BUILD_LONGITUDE = "Longitude";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text, %s text)", TABLE_BUILDING, BUILD_ID, BUILD_NAME, BUILD_LATITUDE, BUILD_LONGITUDE));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text)", TABLE_ROOM, ROOM_ID, ROOM_NAME, ROOM_BUILD_ID));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text)", TABLE_LESSON, LESSON_ID, LESSON_START_TIME, LESSON_END_TIME));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text, %s text)", TABLE_DAY, DAY_ID, DAY_NAME, DAY_NUMBER, DAY_WEEK));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text, %s text)", TABLE_TEACHERS, TEACHERS_ID, TEACHERS_NAME, TEACHERS_SHORT, TEACHERS_DEGREE));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text)", TABLE_DISCIPLINE, DISC_ID, DISC_NAME, DISC_FULL_NAME));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text)", TABLE_GROUP_DISC_RELATIONS, GDR_ID, GDR_GROUP_ID, GDR_DISC_ID));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text, %s text, %s text, %s text)", TABLE_TABLING, TABLING_ID, TABLING_DAY_ID, TABLING_DISC_ID, TABLING_LESSON_ID,TABLING_ROOM_ID,TABLING_TYPE));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text)", TABLE_TEACH_DISC_RELATIONS, TDR_ID, TDR_TEACHERS_ID, TDR_DISCIPLINE_ID));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text)", TABLE_GROUP, GROUP_ID, GROUP_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(String.format("drop table if exists %s", TABLE_BUILDING));
        db.execSQL(String.format("drop table if exists %s", TABLE_ROOM));
        db.execSQL(String.format("drop table if exists %s", TABLE_LESSON));
        db.execSQL(String.format("drop table if exists %s", TABLE_DAY));
        db.execSQL(String.format("drop table if exists %s", TABLE_TEACHERS));
        db.execSQL(String.format("drop table if exists %s", TABLE_DISCIPLINE));
        db.execSQL(String.format("drop table if exists %s", TABLE_GROUP_DISC_RELATIONS));
        db.execSQL(String.format("drop table if exists %s", TABLE_TABLING));
        db.execSQL(String.format("drop table if exists %s", TABLE_TEACH_DISC_RELATIONS));
        db.execSQL(String.format("drop table if exists %s", TABLE_GROUP));
        onCreate(db);
    }
}
