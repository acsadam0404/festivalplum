package hu.festivalplum.festival.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.festival.adapter.FestivalViewAdapter;
import hu.festivalplum.model.FestivalObject;

/**
 * Created by viktor on 2015.03.26..
 */
public class ProgramFragment extends MyFragment {

    private List<String> headerTitles;
    private Map<String, List<FestivalObject>> childTitles;

    private ExpandableListView expandableListView;

    public ProgramFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = getActivity();
        expandableListView = new ExpandableListView(context);
        childTitles = ((FestivalActivity)context).getFestivalChild();
        headerTitles = ((FestivalActivity)context).getFestivalGroup();
        festivalViewAdapter = new FestivalViewAdapter(context, headerTitles, childTitles);
        expandableListView.setAdapter(festivalViewAdapter);

        return expandableListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < festivalViewAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    public int getName() {
        return R.string.tab_program;
    }
}
