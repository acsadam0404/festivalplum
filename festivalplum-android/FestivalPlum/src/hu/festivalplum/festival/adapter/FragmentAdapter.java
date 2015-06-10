package hu.festivalplum.festival.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import hu.festivalplum.festival.fragment.InfoFragment;
import hu.festivalplum.festival.fragment.MapFragment;
import hu.festivalplum.festival.fragment.MyFragment;
import hu.festivalplum.festival.fragment.ProgramFragment;


/**
 * Created by viktor on 2015.03.26..
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<MyFragment> fragments;
    private String[] titles;

    public FragmentAdapter(FragmentManager fm, Context context, boolean createMap) {
        super(fm);
        fragments = new ArrayList<MyFragment>();
        fragments.add(new ProgramFragment());
        if(createMap)
            fragments.add(new MapFragment());
        fragments.add(new InfoFragment());
        titles = new String[fragments.size()];
        for(int i = 0; i < fragments.size(); i++){
            titles[i] = context.getResources().getString(fragments.get(i).getName());
        }
    }

    public void refreshView(){
        for (MyFragment f : fragments){
            f.refreshView();
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
