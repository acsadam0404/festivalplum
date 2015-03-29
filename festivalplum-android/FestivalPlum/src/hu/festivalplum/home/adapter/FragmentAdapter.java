package hu.festivalplum.home.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import hu.festivalplum.FPApplication;
import hu.festivalplum.home.fragment.FragmentBand;
import hu.festivalplum.home.fragment.FragmentCity;
import hu.festivalplum.home.fragment.FragmentName;
import hu.festivalplum.home.fragment.FragmentTime;
import hu.festivalplum.home.fragment.MyFragment;


/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<MyFragment> fragments;
    private String[] titles;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new FragmentName());
        fragments.add(new FragmentTime());
        fragments.add(new FragmentCity());
        fragments.add(new FragmentBand());
        titles = new String[fragments.size()];
        for(int i = 0; i < fragments.size(); i++){
            titles[i] = context.getResources().getString(fragments.get(i).getName());
        }
    }

    public void filterInView(String query){
        for (MyFragment f : fragments){
            f.filterInView(query);
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
