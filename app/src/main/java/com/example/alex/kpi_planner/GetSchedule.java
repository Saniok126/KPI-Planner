//package com.example.alex.kpi_planner;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.ListAdapter;
//import android.widget.SimpleAdapter;
//import android.widget.Toast;
//
//import com.example.alex.kpi_planner.dataClasses.Tabling;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//
///**
// * Created by Sonder on 14.12.2017.
// */
//
//public class GetSchedule extends AsyncTask<Void, Void, Void> {
//
//    private String TAG = ScheduleFragment.class.getSimpleName();
//    private static String urlSchedule = "https://api.rozklad.hub.kpi.ua/groups/594/timetable.json/";
//    private DBHelper dbHelper;
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        // Showing progress dialog
//        //pDialog = new ProgressDialog(MySchedule.this);
//        //pDialog.setMessage("Please wait...");
//        //pDialog.setCancelable(false);
//        //pDialog.show();
//
//    }
//
//    @Override
//    protected Void doInBackground(Void... arg0) {
//        HttpHandler sh = new HttpHandler();
//        dbHelper = new DBHelper(MySchedule.this);
//
//        // Making a request to url and getting response
//        String jsonStr = sh.makeServiceCall(urlSchedule);
//
//        Log.e(TAG, "Response from url: " + jsonStr);
//
//        if (jsonStr != null) {
//            try {
//                JSONObject jsonObj = new JSONObject(jsonStr);
//
//                // Getting JSON Array node
//                JSONObject scheduleData = jsonObj.getJSONObject("data");
//                String weekNames[] = {"1","2"};
//                for (String weekDate : weekNames) {
//                    JSONObject week = scheduleData.getJSONObject(weekDate);
//                    JSONArray daysNumber = week.names();
//                    Log.e(TAG, "Week: " + weekDate);
//                    for (int i = 0; i < daysNumber.length(); i++) {
//                        String number = daysNumber.getString(i);
//                        JSONObject day = week.getJSONObject(number);
//                        JSONArray lessonsNumber = day.names();
//
//                        String dayId = dbHelper.selectDay(weekDate, number).getId();
//
//                        Log.e(TAG, "Day: " + number );//+ " : " + day);
//                        for (int k = 0; k < lessonsNumber.length(); k++) {
//                            String para = lessonsNumber.getString(k);
//                            JSONObject lesson = day.getJSONObject(para);
//
//                            String id = lesson.getString("id");
//                            String type = lesson.getString("type");
//
//                            String discNumber = lesson.getJSONObject("discipline").getString("id");
//                            String discName = lesson.getJSONObject("discipline").getString("name");
//                            String discFullName = lesson.getJSONObject("discipline").getString("full_name");
//                            String discId = Long.toString(dbHelper.insertDisc(discNumber, discName, discFullName));
//
//                            Tabling tabling = new Tabling("0", dayId, discId, para, type);
//                            dbHelper.insertTabling(tabling);
//
//                            // TODO: Group, Teacher, Rooms (JSONArray)
//
//
//                            // tmp hash map for single lesson
//                            HashMap<String, String> singleLesson = new HashMap<>();
//
//                            singleLesson.put("id", id);
//                            singleLesson.put("name", discFullName);
//                            singleLesson.put("type", type);
//
//                            contactList.add(singleLesson);
//
//                            Log.e(TAG, "Para: " + para + " : " + lesson);
//                        }
//                    }
//                }
//            } catch (final JSONException e) {
//                Log.e(TAG, "Json parsing error: " + e.getMessage());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Json parsing error: " + e.getMessage(),
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//
//            }
//        } else {
//            Log.e(TAG, "Couldn't get json from server.");
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),
//                            "Couldn't get json from server. Check LogCat for possible errors!",
//                            Toast.LENGTH_LONG)
//                            .show();
//                }
//            });
//
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        super.onPostExecute(result);
//        // Dismiss the progress dialog
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//        /**
//         * Updating parsed JSON data into ListView
//         * */
//        ListAdapter adapter = new SimpleAdapter(
//                MySchedule.this, contactList,
//                R.layout.list_item, new String[]{"name", "email", "mobile"},
//                new int[]{R.id.name, R.id.email, R.id.mobile});
//
//        lv.setAdapter(adapter);
//    }
//
//}
//
//}
