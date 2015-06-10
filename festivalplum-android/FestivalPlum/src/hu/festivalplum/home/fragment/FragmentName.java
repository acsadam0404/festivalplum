package hu.festivalplum.home.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Switch;

import java.util.List;

import hu.festivalplum.FPApplication;
import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.home.adapter.NameGridViewAdapter;
import hu.festivalplum.home.adapter.NameViewAdapter;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.SideBar;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentName extends MyFragment {

    private List<HomeObject> homeData;

    private AdapterView list;
    private boolean isList;

    public FragmentName() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();

        homeData = FPApplication.getInstence().getNameList();
        isList = ((HomeActivity)context).isList();
        boolean highPriority = ((HomeActivity)context).isHighPriority();
        View contentView;
        if(isList){
            contentView = inflater.inflate(R.layout.fragment_ctrl_alphabetic, container, false);
        }else{
            contentView = inflater.inflate(R.layout.fragment_ctrl_grid_alphabetic, container, false);
        }
        final Switch priority = (Switch) contentView.findViewById(R.id.switch1);
        priority.setChecked(highPriority);
        priority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nameViewAdapter.notifyDataSetChanged(isChecked);
            }
        });

        if(isList){
            list = (ListView) contentView.findViewById(R.id.myListView);
            nameViewAdapter = new NameViewAdapter(context, homeData, priority.isChecked());
        }
        else{
            list = (GridView) contentView.findViewById(R.id.myGridView);
            nameViewAdapter = new NameGridViewAdapter(context, homeData, priority.isChecked());
        }

        list.setAdapter(nameViewAdapter);
        SideBar indexBar = (SideBar) contentView.findViewById(R.id.sideBar);
        indexBar.setView(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeObject object = (HomeObject) list.getAdapter().getItem(i);
                Intent intent = new Intent(context, FestivalActivity.class);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                intent.putExtra("isFestival", object.isFestival());
                intent.putExtra("info", object.getPlaceInfo());
                intent.putExtra("map", object.getPlaceMap());
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
