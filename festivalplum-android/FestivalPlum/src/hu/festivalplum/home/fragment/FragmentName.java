package hu.festivalplum.home.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.FPApplication;
import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.home.adapter.HomeViewAdapter;
import hu.festivalplum.home.adapter.NameViewAdapter;
import hu.festivalplum.model.BandObject;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.SideBar;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentName extends MyFragment {

    private List<HomeObject> homeData;

    private ListView list;

    public FragmentName() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();

        View contentView = inflater.inflate(R.layout.fragment_alphabetic, container, false);
        list = (ListView) contentView.findViewById(R.id.myListView);

        homeData = FPApplication.getInstence().getNameList();

        nameViewAdapter = new NameViewAdapter(context, homeData);
        list.setAdapter(nameViewAdapter);
        SideBar indexBar = (SideBar) contentView.findViewById(R.id.sideBar);
        indexBar.setListView(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, FestivalActivity.class);
                HomeObject object = (HomeObject) list.getAdapter().getItem(i);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                context.startActivity(intent);
            }
        });

        return contentView;
    }

    @Override
    public int getName() {
        return R.string.tab_name;
    }

}
