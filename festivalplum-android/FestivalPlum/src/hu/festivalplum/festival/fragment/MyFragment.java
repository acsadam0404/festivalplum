package hu.festivalplum.festival.fragment;

import android.support.v4.app.Fragment;

import hu.festivalplum.festival.adapter.FestivalExpViewAdapter;
import hu.festivalplum.festival.adapter.FestivalViewAdapter;


/**
 * Created by viktor on 2015.03.26..
 */
public abstract class MyFragment extends Fragment {
    protected FestivalExpViewAdapter festivalExpViewAdapter;
    protected FestivalViewAdapter festivalViewAdapter;
    protected String query;

    public abstract int getName();

    public void refreshView(){
        if(festivalExpViewAdapter != null)
            festivalExpViewAdapter.notifyDataSetChanged();
        if(festivalViewAdapter != null)
            festivalViewAdapter.notifyDataSetChanged();
    }

    public void
    filterInView(String query){
        this.query = query;
        if(festivalExpViewAdapter != null){
            festivalExpViewAdapter.filter(query);
        }
        if(festivalViewAdapter != null){
            festivalViewAdapter.filter(query);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(query != null && !"".equals(query)){
            if(festivalExpViewAdapter != null){
                festivalExpViewAdapter.filter(query);
            }
            if(festivalViewAdapter != null){
                festivalViewAdapter.filter(query);
            }
        }
    }
}
