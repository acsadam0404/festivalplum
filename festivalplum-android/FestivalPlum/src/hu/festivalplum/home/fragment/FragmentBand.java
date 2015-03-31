package hu.festivalplum.home.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import hu.festivalplum.FPApplication;
import hu.festivalplum.R;
import hu.festivalplum.band.BandActivity;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.adapter.BandViewAdapter;
import hu.festivalplum.model.BandObject;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.SideBar;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentBand extends MyFragment {

    private List<BandObject> bandData;
    private ListView list;

    public FragmentBand() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();
        View contentView = inflater.inflate(R.layout.fragment_alphabetic, container, false);
        list = (ListView) contentView.findViewById(R.id.myListView);

        bandData = FPApplication.getInstence().getBandData();

        bandViewAdapter = new BandViewAdapter(context, bandData);
        list.setAdapter(bandViewAdapter);
        SideBar indexBar = (SideBar) contentView.findViewById(R.id.sideBar);
        indexBar.setListView(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, BandActivity.class);
                BandObject object = (BandObject) list.getAdapter().getItem(i);
                intent.putExtra("bandId", object.getBandId());
                intent.putExtra("info", object.getHtmlInfo());
                intent.putExtra("name", object.getName());
                context.startActivity(intent);
            }
        });

        return contentView;
    }

    @Override
    public int getName() {
        return R.string.tab_band;
    }


}
