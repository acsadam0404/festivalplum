package hu.festivalplum.home.fragment;

import android.support.v4.app.Fragment;

import hu.festivalplum.home.adapter.HomeViewAdapter;


/**
 * Created by viktor on 2015.03.15..
 */
public abstract class MyFragment extends Fragment {
    protected HomeViewAdapter homeViewAdapter;
    protected String query;

    public abstract String getName();

    public void
    filterInView(String query){
        this.query = query;
        if(homeViewAdapter != null){
            homeViewAdapter.filter(query);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(query != null && !"".equals(query)){
            if(homeViewAdapter != null){
                homeViewAdapter.filter(query);
            }
        }
    }
}
