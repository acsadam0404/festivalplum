package hu.festivalplum.home.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hu.festivalplum.FPApplication;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.home.adapter.BandViewAdapter;
import hu.festivalplum.home.adapter.HomeViewAdapter;
import hu.festivalplum.model.BandObject;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentBand extends MyFragment {

    private static final String NAME = "Fellépő";
    private List<BandObject> bandData;

    public FragmentBand() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();
        ListView v = new ListView(context);
        bandData = FPApplication.getInstence().getBandData();

        v.setAdapter(new BandViewAdapter(context, bandData));

        return v;
    }

    @Override
    public String getName() {
        return NAME;
    }


}
