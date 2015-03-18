package hu.festivalplum.festival;

import android.content.Context;

import android.widget.ExpandableListView;


import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.festivalplum.FestivalActivity;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.home.HomeObject;
import hu.festivalplum.home.HomeViewAdapter;

/**
 * Created by viktor on 2015.03.18..
 */
public class FestivalView  extends ExpandableListView {
    private List<String> headerTitles;
    private Map<String, List<FestivalObject>> childTitles;

    public FestivalView(final Context context) {
        super(context);

        childTitles = ((FestivalActivity)context).getChildTitles();
        headerTitles = ((FestivalActivity)context).getHeaderTitles();
        setAdapter(new FestivalViewAdapter(context, headerTitles, childTitles));
    }


}
