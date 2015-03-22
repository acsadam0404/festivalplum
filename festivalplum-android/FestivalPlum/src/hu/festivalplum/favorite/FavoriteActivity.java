package hu.festivalplum.favorite;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;

public class FavoriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView v = new ListView(this);
        List<HomeObject> items;
        SQLiteUtil sqLite = SQLiteUtil.getInstence(this);
        List<String> eventIds = sqLite.getFavoriteIds("Event");
        items = ParseDataCollector.collectFavoriteData(eventIds);

        v.setAdapter(new FavoriteViewAdapter(this, items));
        setContentView(v);
    }
}
