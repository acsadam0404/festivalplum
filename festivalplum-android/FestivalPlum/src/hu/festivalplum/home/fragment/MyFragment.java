package hu.festivalplum.home.fragment;

import android.support.v4.app.Fragment;

import hu.festivalplum.home.adapter.HomeViewAdapter;


/**
 * Created by viktor on 2015.03.15..
 */
public abstract class MyFragment extends Fragment {
    protected HomeViewAdapter homeViewAdapter;

    public abstract String getName();

    public void refreshView(){
        if(homeViewAdapter != null)
            homeViewAdapter.notifyDataSetChanged();
    }
}
