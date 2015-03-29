package hu.festivalplum.home.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import hu.festivalplum.FPApplication;
import hu.festivalplum.R;
import hu.festivalplum.home.adapter.BandViewAdapter;
import hu.festivalplum.model.BandObject;
import hu.festivalplum.utils.SideBar;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentBand extends MyFragment {

    private List<BandObject> bandData;

    public FragmentBand() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();
        View contentView = inflater.inflate(R.layout.fragment_alphabetic, container, false);
        ListView list = (ListView) contentView.findViewById(R.id.myListView);

        bandData = FPApplication.getInstence().getBandData();

        bandViewAdapter = new BandViewAdapter(context, bandData);
        list.setAdapter(bandViewAdapter);
        SideBar indexBar = (SideBar) contentView.findViewById(R.id.sideBar);
        indexBar.setListView(list);

        return contentView;
    }

    @Override
    public int getName() {
        return R.string.tab_band;
    }


}
