package hu.festivalplum.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import hu.festivalplum.FestivalActivity;

/**
 * Created by Ács Ádám on 2015.02.20..
 */
public class TimeView extends ExpandableListView {
    private List<String> headerTitles;
    private Map<String, List<HomeObject>> childTitles;

    public TimeView(final Context context) {
        super(context);

        childTitles = ((HomeActivity)context).getTimeChildTitles();
        headerTitles = ((HomeActivity)context).getTimeHeaderTitles();
        setAdapter(new HomeViewAdapter(context, headerTitles, childTitles));

        setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FestivalActivity.class);
                context.startActivity(intent);
                return true;
            }
        });
    }

}
