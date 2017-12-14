package com.example.alex.kpi_planner;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.kpi_planner.dataClasses.Building;
import com.example.alex.kpi_planner.dataClasses.Day;
import com.example.alex.kpi_planner.dataClasses.Tabling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    private ScheduleFragment currentFragment;

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

        //jsonParsing();
        dbHelper = new DBHelper(this);
        new GetContacts().execute();

    }


    // *****************  JSON  ***************


    private String TAG = ScheduleFragment.class.getSimpleName();
    private static String urlSchedule = "https://api.rozklad.hub.kpi.ua/groups/594/timetable.json/";
    private ProgressDialog pDialog;

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BaseActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlSchedule);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject scheduleData = jsonObj.getJSONObject("data");
                    String weekNames[] = {"1","2"};
                    for (String weekDate : weekNames) {
                        JSONObject week = scheduleData.getJSONObject(weekDate);
                        JSONArray daysNumber = week.names();
                        Log.e(TAG, "Week: " + weekDate);
                        for (int i = 0; i < daysNumber.length(); i++) {
                            String number = daysNumber.getString(i);
                            JSONObject day = week.getJSONObject(number);
                            JSONArray lessonsNumber = day.names();

                            String dayId = dbHelper.selectDay(weekDate, number).getId();

                            Log.e(TAG, "Day: " + number );//+ " : " + day);
                            for (int k = 0; k < lessonsNumber.length(); k++) {
                                String para = lessonsNumber.getString(k);
                                JSONObject lesson = day.getJSONObject(para);

                                String id = lesson.getString("id");
                                String type = lesson.getString("type");

                                String discNumber = lesson.getJSONObject("discipline").getString("id");
                                String discName = lesson.getJSONObject("discipline").getString("name");
                                String discFullName = lesson.getJSONObject("discipline").getString("full_name");
                                String discId = Long.toString(dbHelper.insertDisc(discNumber, discName, discFullName));

                                Tabling tabling = new Tabling("0", dayId, discId, para, type);
                                dbHelper.insertTabling(tabling);

                                // TODO: Group, Teacher, Rooms (JSONArray)

                                Log.e(TAG, "Para: " + para + " : " + lesson);
                            }
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Schedule uploaded", Toast.LENGTH_SHORT).show();

        }

    }



}
