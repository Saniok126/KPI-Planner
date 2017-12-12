package com.example.alex.kpi_planner;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText etName,etNumb,etWeek;

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

    public void addClick(View view) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        String mondey = "Mondey";
        String tuesday = "Tuesday";
        String wednesday = "Wednesday";
        String thursday = "Thursday";
        String friday = "Friday";
        String saturday = "Saturday";
        String week1 = "1";
        String week2 = "2";
        String number = "1";
        long newRowId;
        String [] days = {mondey,tuesday,wednesday,thursday,friday,saturday};

       for (int i=0;i<days.length;i++)
       {
           contentValues.put(DBHelper.DAY_NUMBER,i+1);
           contentValues.put(DBHelper.DAY_NAME,days[i]);
           contentValues.put(DBHelper.DAY_WEEK,week2);
           newRowId = database.insert(DBHelper.TABLE_DAY,null,contentValues);
           if (newRowId == -1)
           {
               Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
           }
           else
           {
               Toast.makeText(this,"Добавлено: " + newRowId,Toast.LENGTH_SHORT).show();
           }
       }


        dbHelper.close();
    }

    public void readClick(View view) {
      //  SQLiteDatabase database = dbHelper.getWritableDatabase();
       // database.delete(DBHelper.TABLE_DAY,null,null);
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_BUILDING));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_ROOM));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_LESSON));database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_DAY));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_TEACHERS));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_DISCIPLINE));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_GROUP_DISC_RELATIONS));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_TABLING));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_TEACH_DISC_RELATIONS));
         //database.execSQL(String.format("drop table if exists %s", DBHelper.TABLE_GROUP));

     //  dbHelper.onCreate(database);
       // Toast.makeText(this,"БД удалена: ",Toast.LENGTH_SHORT).show();

    }

    public void checkClick(View view) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.DAY_ID,
                DBHelper.DAY_NAME,
                DBHelper.DAY_WEEK,
                DBHelper.DAY_NUMBER};




        dbHelper.close();
    }
}
