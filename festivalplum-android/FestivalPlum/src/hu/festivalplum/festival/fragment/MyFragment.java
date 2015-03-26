package hu.festivalplum.festival.fragment;

import android.support.v4.app.Fragment;

import hu.festivalplum.festival.adapter.FestivalViewAdapter;


/**
 * Created by viktor on 2015.03.26..
 */
public abstract class MyFragment extends Fragment {
    protected FestivalViewAdapter festivalViewAdapter;
    protected String query;

    public abstract String getName();

    public void refreshView(){
        if(festivalViewAdapter != null)
            festivalViewAdapter.notifyDataSetChanged();
    }

    public void
    filterInView(String query){
        this.query = query;
        if(festivalViewAdapter != null){
            festivalViewAdapter.filter(query);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(query != null && !"".equals(query)){
            if(festivalViewAdapter != null){
                festivalViewAdapter.filter(query);
            }
        }
    }
}
