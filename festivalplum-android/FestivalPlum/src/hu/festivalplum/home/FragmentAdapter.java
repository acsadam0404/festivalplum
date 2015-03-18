package hu.festivalplum.home;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<MyFragment> fragments;
    private String[] titles;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<MyFragment>();
        fragments.add(new FragmentName());
        fragments.add(new FragmentTime());
        fragments.add(new FragmentCity());
        fragments.add(new FragmentBand());
        titles = new String[fragments.size()];
        for(int i = 0; i < fragments.size(); i++){
            titles[i] = fragments.get(i).getName();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
