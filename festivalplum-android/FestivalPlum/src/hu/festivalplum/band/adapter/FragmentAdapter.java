package hu.festivalplum.band.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import hu.festivalplum.band.fragment.ConcertFragment;
import hu.festivalplum.band.fragment.InfoFragment;
import hu.festivalplum.band.fragment.MyFragment;

/**
 * Created by viktor on 2015.03.31..
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<MyFragment> fragments;
    private String[] titles;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragments = new ArrayList<MyFragment>();
        fragments.add(new InfoFragment());
        fragments.add(new ConcertFragment());
        titles = new String[fragments.size()];
        for(int i = 0; i < fragments.size(); i++){
            titles[i] = context.getResources().getString(fragments.get(i).getName());
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
