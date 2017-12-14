package com.example.alex.kpi_planner;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.kpi_planner.dataClasses.Building;
import com.example.alex.kpi_planner.dataClasses.Day;

public class BaseActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content, new ScheduleFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.content, new SubjectsFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.content, new TeachersFragment()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);





        dbHelper = new DBHelper(this);
    }

    //*************** QUERIES *********************

    public void addClick(View view) {
        long totalRowIndex = dbHelper.insertBuilding("15", "111", "222");
        if (totalRowIndex == -1) {
            Toast.makeText(this,"Error: building didn't add",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Completed. Total rows: " + totalRowIndex,Toast.LENGTH_SHORT).show();
        }
    }

    public void addLessonClick(View view) {
        long totalRowIndex = dbHelper.insertLessons();
        if (totalRowIndex == -1) {
            Toast.makeText(this,"Error: days didn't add",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Completed. Total rows: " + totalRowIndex,Toast.LENGTH_SHORT).show();
        }
    }

    public void checkClick(View view) {
        Building building = dbHelper.selectBuilding("15");
        if (building != null)
            Toast.makeText(this, "building id: " + building.getId(), Toast.LENGTH_SHORT).show();
    }


    public void addDayClick(View view) {
        long totalRowIndex = dbHelper.insertDays();
        if (totalRowIndex == -1) {
            Toast.makeText(this,"Error: days didn't add",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Completed. Total rows: " + totalRowIndex,Toast.LENGTH_SHORT).show();
        }
    }

    public void checkDayClick(View view) {
        Day day = dbHelper.selectDay("2","5");
        if (day != null)
            Toast.makeText(this, day.getId() + " " + day.getName(), Toast.LENGTH_SHORT).show();
    }

    public void readClick(View view) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //database.delete(DBHelper.TABLE_DAY,null,null);
        dbHelper.onUpgrade(database,1,1);
        Toast.makeText(this,"БД удалена: ",Toast.LENGTH_SHORT).show();

    }

}
