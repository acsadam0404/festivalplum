package hu.festivalplum.home.fragment;

import android.support.v4.app.Fragment;

import hu.festivalplum.home.adapter.BandViewAdapter;
import hu.festivalplum.home.adapter.HomeViewAdapter;
import hu.festivalplum.home.adapter.NameViewAdapter;


/**
 * Created by viktor on 2015.03.15..
 */
public abstract class MyFragment extends Fragment {
    protected HomeViewAdapter homeViewAdapter;
    protected BandViewAdapter bandViewAdapter;
    protected NameViewAdapter nameViewAdapter;
    protected String query;

    public abstract int getName();

    public void
    filterInView(String query){
        this.query = query;
        if(homeViewAdapter != null){
            homeViewAdapter.filter(query);
        }else if(bandViewAdapter != null){
            bandViewAdapter.filter(query);
        }else if(nameViewAdapter != null){
            nameViewAdapter.filter(query);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(query != null && !"".equals(query)){
            if(homeViewAdapter != null){
                homeViewAdapter.filter(query);
            }else if(bandViewAdapter != null){
                bandViewAdapter.filter(query);
            }else if(nameViewAdapter != null){
                nameViewAdapter.filter(query);
            }
        }
    }
}
