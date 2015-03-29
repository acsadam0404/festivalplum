package hu.festivalplum.home.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.FPApplication;
import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.home.adapter.HomeViewAdapter;
import hu.festivalplum.model.HomeObject;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentCity extends MyFragment {

    private List<String> headerTitles;
    private Map<String, List<HomeObject>> childTitles;

    public FragmentCity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = getActivity();
        ExpandableListView v = new ExpandableListView(context);
        childTitles = FPApplication.getInstence().getCityChild();
        headerTitles = FPApplication.getInstence().getCityGroup();
        homeViewAdapter = new HomeViewAdapter(context, headerTitles, childTitles);
        v.setAdapter(homeViewAdapter);


        v.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                Intent intent = new Intent(context, FestivalActivity.class);
                HomeObject object = (HomeObject) expandableListView.getExpandableListAdapter().getChild(i, i2);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                context.startActivity(intent);
                return true;
            }
        });
        return v;
    }

    @Override
    public int getName() {
        return R.string.tab_city;
    }

}
