package com.example.alex.kpi_planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.kpi_planner.dataClasses.Building;
import com.example.alex.kpi_planner.dataClasses.Day;

/**
 * Created by Saniok on 11.12.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "groupsDB";

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
    public static final String TABLING_TEACHER = "Teacher";

    public static final String TABLE_DISCIPLINE = "Discipline";
    public static final String DISC_ID = "_id";
    public static final String DISC_NAME = "Name";
    public static final String DISC_FULL_NAME = "Full_name";
    public static final String DISC_NUMBER = "Number";

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
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text, %s text)", TABLE_DISCIPLINE, DISC_ID, DISC_NAME, DISC_FULL_NAME, DISC_NUMBER));
        db.execSQL(String.format("create table %s (%s integer primary key, %s text, %s text, %s text, %s text, %s text, %s text)", TABLE_TABLING, TABLING_ID, TABLING_DAY_ID, TABLING_DISC_ID, TABLING_LESSON_ID,TABLING_ROOM_ID,TABLING_TYPE, TABLING_TEACHER));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("drop table if exists %s", TABLE_BUILDING));
        db.execSQL(String.format("drop table if exists %s", TABLE_ROOM));
        db.execSQL(String.format("drop table if exists %s", TABLE_LESSON));
        db.execSQL(String.format("drop table if exists %s", TABLE_DAY));
        db.execSQL(String.format("drop table if exists %s", TABLE_DISCIPLINE));
        db.execSQL(String.format("drop table if exists %s", TABLE_TABLING));
        onCreate(db);
    }

    public long insertDays(){
        SQLiteDatabase database = getWritableDatabase();
        long newRowId = -1;
        if (countDays() <= 0) {
            ContentValues contentValues = new ContentValues();
            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            String[] weeks = {"1", "2"};
            for (int weekId = 0; weekId < weeks.length; weekId++) {
                for (int i = 0; i < days.length; i++) {
                    contentValues.put(DBHelper.DAY_NUMBER, i + 1);
                    contentValues.put(DBHelper.DAY_NAME, days[i]);
                    contentValues.put(DBHelper.DAY_WEEK, weeks[weekId]);
                    newRowId = database.insert(DBHelper.TABLE_DAY, null, contentValues);
                }
            }
        }
        return newRowId;
    }

    public Day selectDay(String week, String dayNumber){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = String.format(
                "SELECT %s, %s FROM %s WHERE %s = %s AND %s = %s",
                DAY_ID, DAY_NAME, TABLE_DAY, DAY_WEEK, week, DAY_NUMBER, dayNumber);

        Cursor c = db.rawQuery(selectQuery, null);

        Day day = new Day();
        if (c != null) {
            c.moveToFirst();

            if (c.getCount() > 0) {
                day.setId(c.getString(c.getColumnIndex(DAY_ID)));
                day.setName(c.getString(c.getColumnIndex(DAY_NAME)));
            }
        }
        return day;
    }

    public int countDays(){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = String.format(
                "SELECT Count(*) FROM %s",
                TABLE_DAY);

        Cursor c = db.rawQuery(selectQuery, null);

        int count = 0;
        if (c != null) {
            c.moveToFirst();
            count = c.getInt(0);
        }
        Log.e(DBHelper.DATABASE_NAME, " " + count);
        return count;
    }

    public long insertLessons(){
        SQLiteDatabase database = getWritableDatabase();
        long newRowId = -1;
        //database.delete(TABLE_LESSON,null,null);
        if (countLessons() <= 0) {
            ContentValues contentValues = new ContentValues();
            String[] startTime = {"8:30", "10:25", "12:20", "14:15", "16:10", "18:30", "20:20"};
            String[] endTime = {"10:05", "12:00", "13:55", "15:50", "17:45", "20:05", "21:55"};
            for (int i = 0; i < startTime.length; i++) {
                //contentValues.put(DBHelper.LESSON_END_TIME, i + 1);
                contentValues.put(LESSON_START_TIME, startTime[i]);
                contentValues.put(LESSON_END_TIME, endTime[i]);
                newRowId = database.insert(TABLE_LESSON, null, contentValues);
            }
        }
        return newRowId;
    }

    public int countLessons(){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = String.format(
                "SELECT Count(*) FROM %s",
                TABLE_LESSON);

        Cursor c = db.rawQuery(selectQuery, null);

        int count = 0;
        if (c != null) {
            c.moveToFirst();
            count = c.getInt(0);
        }
        Log.e(DBHelper.DATABASE_NAME, " " + count);
        return count;
    }

/*    public Day selectLesson(String number){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = String.format(
                "SELECT %s, %s FROM %s WHERE %s = %s AND %s = %s",
                DAY_ID, DAY_NAME, TABLE_DAY, DAY_WEEK, week, DAY_NUMBER, dayNumber);

        Cursor c = db.rawQuery(selectQuery, null);

        Day day = new Day();
        if (c != null) {
            c.moveToFirst();

            if (c.getCount() > 0) {
                day.setId(c.getString(c.getColumnIndex(DAY_ID)));
                day.setName(c.getString(c.getColumnIndex(DAY_NAME)));
            }
        }
        return day;
    }*/

    public long insertBuilding(String name, String latitude, String longitude){
        SQLiteDatabase database = getWritableDatabase();
        long newRowId;
        Building building = selectBuilding(name);
        if (building.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BUILD_NAME, name);
            contentValues.put(BUILD_LATITUDE, latitude);
            contentValues.put(BUILD_LONGITUDE, longitude);
            newRowId = database.insert(TABLE_BUILDING, null, contentValues);
        }
        else newRowId = Long.parseLong(building.getId());
        return newRowId;
    }

    public Building selectBuilding(String name){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = String.format(
                "SELECT * FROM %s WHERE %s = %s",
                TABLE_BUILDING, BUILD_NAME, name);

        Cursor c = db.rawQuery(selectQuery, null);

        Building building = new Building();
        if (c != null) {
            c.moveToFirst();

            if (c.getCount() > 0) {
                building.setId(c.getString(c.getColumnIndex(BUILD_ID)));
                building.setNumber(c.getString(c.getColumnIndex(BUILD_NAME)));
                building.setLatitude(c.getString(c.getColumnIndex(BUILD_LATITUDE)));
                building.setLongitude(c.getString(c.getColumnIndex(BUILD_LONGITUDE)));
            }
        }
        return building;
    }



}
