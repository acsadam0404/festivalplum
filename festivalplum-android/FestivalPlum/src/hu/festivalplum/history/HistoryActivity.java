package hu.festivalplum.history;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.FPApplication;
import hu.festivalplum.R;
import hu.festivalplum.favorite.FavoriteActivity;
import hu.festivalplum.favorite.FavoriteViewAdapter;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.adapter.HomeViewAdapter;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;

/**
 * Created by viktor on 2015.03.27..
 */
public class HistoryActivity extends Activity {

    private HomeViewAdapter mAdapter;
    private List<String> headerTitles;
    private Map<String, List<HomeObject>> childTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater infalInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = infalInflater.inflate(R.layout.activity_explistview, null);
        setContentView(contentView);
        ExpandableListView v = (ExpandableListView) findViewById(R.id.expandableListView);

        headerTitles = FPApplication.getInstence().getHistoryTimeGroup();
        childTitles = FPApplication.getInstence().getHistoryTimeChild();

        mAdapter = new HomeViewAdapter(this, headerTitles, childTitles);
        v.setAdapter(mAdapter);
        v.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                Intent intent = new Intent(HistoryActivity.this, FestivalActivity.class);
                HomeObject object = (HomeObject) expandableListView.getExpandableListAdapter().getChild(i, i2);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                HistoryActivity.this.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.filter(s);
                return false;
            }
        });

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (id){
            case R.id.action_settings:
                //
                break;
            case R.id.action_search:
                //
                break;
            case R.id.action_favourite:
                i = new Intent(this, FavoriteActivity.class);
                this.startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
