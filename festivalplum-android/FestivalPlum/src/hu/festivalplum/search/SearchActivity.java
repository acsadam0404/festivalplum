package hu.festivalplum.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

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

        ListView v = new ListView(this);
        mAdapter = new SearcViewAdapter(this);
        v.setAdapter(mAdapter);
        setContentView(v);

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
