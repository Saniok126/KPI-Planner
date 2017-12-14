package com.example.alex.kpi_planner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.alex.kpi_planner.dataClasses.Tabling;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

/**
 * Created by Sonder on 14.12.2017.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private DBHelper dbHelper;

    @Before
    public void setUp() throws Exception {
        dbHelper = new DBHelper(getInstrumentation().getTargetContext());
        //dbHelper = new DBHelper(InstrumentationRegistry.getContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(database,1,1);
    }


    @Test
    public void buildingTest() throws Exception {
        assertEquals(1, dbHelper.insertBuilding("15", "111", "222"));
        assertEquals("1", dbHelper.selectBuilding("15").getId());
        assertEquals(null, dbHelper.selectBuilding("17").getId());
        assertEquals(2, dbHelper.insertBuilding("17", "111", "222"));
        assertEquals("2", dbHelper.selectBuilding("17").getId());
    }

    @Test
    public void daysTest() throws Exception {
        assertEquals(null, dbHelper.selectDay("1", "3").getId());
        assertEquals(0, dbHelper.countDays());
        assertEquals(12, dbHelper.insertDays());
        assertEquals(12, dbHelper.countDays());
        assertEquals("3", dbHelper.selectDay("1", "3").getId());
        assertEquals("7", dbHelper.selectDay("2", "1").getId());
        assertEquals(null, dbHelper.selectDay("3", "3").getId());
    }

    @Test
    public void lessonTest() throws Exception {
        assertEquals(null, dbHelper.selectLesson("1").getStartTime());
        assertEquals(0, dbHelper.countLessons());
        assertEquals(7, dbHelper.insertLessons());
        assertEquals(7, dbHelper.countLessons());
        assertEquals("8:30", dbHelper.selectLesson("1").getStartTime());
        assertEquals("12:20", dbHelper.selectLesson("3").getStartTime());
        assertEquals("12:00", dbHelper.selectLesson("2").getEndTime());
    }

    @Test
    public void roomTest() throws Exception {
        assertEquals(null, dbHelper.selectRoom("323","18").getId());
        assertEquals(1, dbHelper.insertRoom("323","18"));
        assertEquals("18", dbHelper.selectRoom("323","18").getBuildingId());
        assertEquals("1", dbHelper.selectRoom("323","18").getId());
        assertEquals(2, dbHelper.insertRoom("339","18"));
    }

    @Test
    public void disciplineTest() throws Exception {
        assertEquals(null, dbHelper.selectDisc("100").getId());
        assertEquals(1, dbHelper.insertDisc("100","Mobile", "Mobile dev"));
        assertEquals(1, dbHelper.insertDisc("100","Mobile", "Mobile dev"));
        assertEquals(2, dbHelper.insertDisc("200","Mobile2", "Mobile dev2"));
        assertEquals("2", dbHelper.selectDisc("200").getId());
        assertEquals("Mobile dev2", dbHelper.selectDisc("200").getFullName());
    }

    @Test
    public void tablingTest() throws Exception {
        Tabling tabling = new Tabling("0", "2", "3", "2", "1");
        Tabling tabling2 = new Tabling("0", "2", "1", "3", "2");

        assertEquals(null, dbHelper.selectTabling(tabling).getId());
        assertEquals(1, dbHelper.insertTabling(tabling));
        assertEquals(1, dbHelper.insertTabling(tabling));
        assertEquals(2, dbHelper.insertTabling(tabling2));
        assertEquals("1", dbHelper.selectTabling(tabling).getId());
        assertEquals("2", dbHelper.selectTabling(tabling2).getId());
        assertEquals("2", dbHelper.selectTabling(tabling).getDayId());
        assertEquals("3", dbHelper.selectTabling(tabling).getDisciplineId());
        assertEquals("2", dbHelper.selectTabling(tabling).getLessonId());
        assertEquals("1", dbHelper.selectTabling(tabling).getType());

    }



    @After
    public void tearDown() throws Exception {
        dbHelper.close();
    }
}
