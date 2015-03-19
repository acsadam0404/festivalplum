package hu.festivalplum.favorite;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hu.festivalplum.FestivalActivity;
import hu.festivalplum.festival.FestivalViewAdapter;
import hu.festivalplum.home.HomeObject;

/**
 * Created by viktor on 2015.03.19..
 */
public class FavoriteView extends ListView {

    List<HomeObject> items;

    public FavoriteView(final Context context) {
        super(context);

        //sqlite
        items = new ArrayList<HomeObject>();
        HomeObject o1 = new HomeObject();
        o1.setCityName("Budapest");
        o1.setPlaceName("Zöldpardon");
        HomeObject o2 = new HomeObject();
        o2.setCityName("Budapest");
        o2.setPlaceName("opkdfk");
        items.add(o1);
        items.add(o2);

        setAdapter(new FavoriteViewAdapter(context, items));
    }
}
