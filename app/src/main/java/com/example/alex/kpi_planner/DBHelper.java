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
    public static final String TABLE_GROUPS = "groups";

    public static final String KEY_ID = "_1";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIL = "mail";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_GROUPS + "(" + KEY_ID +
         "integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_GROUPS);
        onCreate(db);
    }
}
