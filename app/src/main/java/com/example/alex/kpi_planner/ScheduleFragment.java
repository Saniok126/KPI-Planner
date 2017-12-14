package com.example.alex.kpi_planner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ScheduleFragment extends Fragment {

    private ViewPager viewPager;
    private ScheduleFragmentSample scheduleFragmentSample;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new SchedulePagerAdapter(getChildFragmentManager()));

        //???
        TabLayout tabLayout = rootView.findViewById(R.id.dayTabs);
        tabLayout.setupWithViewPager(viewPager);

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

            //View lv = rootView.findViewById(R.id.list);
            //((BaseActivity)getActivity()).jsonParsing(lv);

            return rootView;
        }
    }


    public class SchedulePagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[]{"Tab1", "Tab2", "Tab3"};

        public SchedulePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScheduleFragmentSample.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];


        }
    }

}
