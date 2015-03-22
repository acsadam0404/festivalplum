package hu.festivalplum.favorite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import hu.festivalplum.MapActivity;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

public class FavoriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView v = new ListView(this);

        List<String> eventIds = SQLiteUtil.getInstence(this).getFavoriteIds("Event");
        List<HomeObject> items = ParseDataCollector.collectFavoriteData(eventIds);

        v.setAdapter(new FavoriteViewAdapter(this, items));

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavoriteActivity.this, FestivalActivity.class);
                HomeObject object = (HomeObject) adapterView.getAdapter().getItem(i);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                FavoriteActivity.this.startActivity(intent);
            }
        });

        setContentView(v);
    }

    public void mapHandler (View v) {
        HomeObject item = (HomeObject)v.getTag();
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("eventId", item.getEventId());
        startActivity(intent);
    }
}
