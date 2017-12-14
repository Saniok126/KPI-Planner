package com.example.alex.kpi_planner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.kpi_planner.dataClasses.Discipline;
import com.example.alex.kpi_planner.dataClasses.Tabling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ScheduleFragment extends Fragment {

    private ViewPager viewPager;
    private ScheduleFragmentSample scheduleFragmentSample;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public ListView lv;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new SchedulePagerAdapter(getChildFragmentManager()));

        //???
        TabLayout tabLayout = rootView.findViewById(R.id.dayTabs);
        tabLayout.setupWithViewPager(viewPager);

        //contactList = new ArrayList<>();
        //lv = (ListView) findViewById(R.id.list);
        //viewSchedule(rootView);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Toast.makeText(getActivity(), "Teacher fragment show", Toast.LENGTH_SHORT).show();
        inflater.inflate(R.menu.menu_schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected DBHelper dbHelper;

    public void viewSchedule(View rootView, String  dayId){
        dbHelper = new DBHelper(getContext());
        contactList = new ArrayList<>();
        lv = rootView.findViewById(R.id.list);

        List<Tabling> listTabling = dbHelper.selectTabling(dayId);

        for (int i = 0; i < listTabling.size(); i++) {
            HashMap<String, String> singleLesson = new HashMap<>();
            Tabling table = listTabling.get(i);
            String discId = table.getDisciplineId();
            Discipline discipline = dbHelper.selectDiscById(discId);

            Log.e(DBHelper.DATABASE_NAME, "discId " + table.getDisciplineId() + " fullName " + discipline.getFullName());

            String[] typeLessonList = {"Лекція", "Практична", "Лабораторна"};
            String typeLesson = typeLessonList[Integer.parseInt(table.getType())];

            singleLesson.put("id", table.getLessonId());
            singleLesson.put("name", discipline.getFullName());
            singleLesson.put("type", typeLesson);

            contactList.add(singleLesson);
        }


        ListAdapter adapter = new SimpleAdapter(
                getContext(), contactList,
                R.layout.list_item, new String[]{"name", "id", "type"},
                new int[]{R.id.name, R.id.email, R.id.mobile});

        lv.setAdapter(adapter);

    }



    public static class ScheduleFragmentSample extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ScheduleFragmentSample() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ScheduleFragmentSample newInstance(int sectionNumber) {
            ScheduleFragmentSample fragment = new ScheduleFragmentSample();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_page, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            Log.e("wtf", getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            ((ScheduleFragment)getParentFragment())
                    .viewSchedule(rootView,""+getArguments().getInt(ARG_SECTION_NUMBER));

            return rootView;
        }

    }


    public class SchedulePagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"};

        public SchedulePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScheduleFragmentSample.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];


        }
    }

}
