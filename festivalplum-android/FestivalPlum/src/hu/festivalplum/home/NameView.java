package hu.festivalplum.home;

import android.content.Context;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

/**
 * Created by Ács Ádám on 2015.03.06..
 */
public class NameView extends ExpandableListView {
    private List<String> headerTitles;
    private Map<String, List<HomeObject>> childTitles;
    public NameView(Context context) {
        super(context);

        childTitles = ((HomeActivity)context).getNameChildTitles();
        headerTitles = ((HomeActivity)context).getNameHeaderTitles();
        setAdapter(new HomeViewAdapter(context, headerTitles, childTitles));
    }

}
