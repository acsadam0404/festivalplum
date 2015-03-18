package hu.festivalplum.home;

import android.content.Context;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

/**
 * Created by Ács Ádám on 2015.03.06..
 */
public class SettlementView extends ExpandableListView {
    private List<String> headerTitles;
    private Map<String, List<HomeObject>> childTitles;

    public SettlementView(Context context) {
        super(context);
        childTitles = ((HomeActivity)context).getCityChildTitles();
        headerTitles = ((HomeActivity)context).getCityHeaderTitles();
        setAdapter(new HomeViewAdapter(context, headerTitles, childTitles));
    }

}
