package hu.festivalplum.festival;

import android.content.Context;

import android.widget.ExpandableListView;


import java.util.List;
import java.util.Map;

/**
 * Created by viktor on 2015.03.18..
 */
public class FestivalView  extends ExpandableListView {
    private List<String> headerTitles;
    private Map<String, List<FestivalObject>> childTitles;

    public FestivalView(final Context context) {
        super(context);

        childTitles = ((FestivalActivity)context).getFestivalChild();
        headerTitles = ((FestivalActivity)context).getFestivalGroup();
        setAdapter(new FestivalViewAdapter(context, headerTitles, childTitles));
    }


}
