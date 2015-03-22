package hu.festivalplum.home.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.home.adapter.HomeViewAdapter;
import hu.festivalplum.model.HomeObject;

/**
 * Created by Ács Ádám on 2015.03.06..
 */
public class SettlementView extends ExpandableListView {
    private List<String> headerTitles;
    private Map<String, List<HomeObject>> childTitles;

    public SettlementView(final Context context) {
        super(context);
        childTitles = ((HomeActivity)context).getCityChild();
        headerTitles = ((HomeActivity)context).getCityGroup();
        setAdapter(new HomeViewAdapter(context, headerTitles, childTitles));

        setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                Intent intent = new Intent(context, FestivalActivity.class);
                HomeObject object = (HomeObject)expandableListView.getExpandableListAdapter().getChild(i,i2);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                context.startActivity(intent);
                return true;
            }
        });

    }

}
