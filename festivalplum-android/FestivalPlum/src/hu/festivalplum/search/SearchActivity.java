package hu.festivalplum.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.23..
 */
public class SearchActivity extends Activity {

    private SearcViewAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView v = (ListView) findViewById(R.id.listView);

        mAdapter = new SearcViewAdapter(this);
        v.setAdapter(mAdapter);

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, FestivalActivity.class);
                HomeObject object = (HomeObject) adapterView.getAdapter().getItem(i);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                SearchActivity.this.startActivity(intent);
            }
        });

        handleIntent(getIntent());
    }

    public void likeHandler (View v) {
        HomeObject item = (HomeObject)v.getTag();
        SQLiteUtil.getInstence(this).addFavoriteId("Event", item.getEventId());
        item.setFavorite(!item.isFavorite());
        Utils.setFavoriteImage((ImageView) v, item.isFavorite());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<HomeObject> list = ParseDataCollector.collectSearchData(query);
            mAdapter.refresh(list);
            HomeActivity.modFavorite = true;
        }
    }


}
