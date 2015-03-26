package hu.festivalplum.festival;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.favorite.FavoriteActivity;
import hu.festivalplum.festival.adapter.FestivalViewAdapter;
import hu.festivalplum.festival.adapter.FragmentAdapter;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;


public class FestivalActivity extends FragmentActivity {

    private List<String> festivalGroup;
    private Map<String, List<FestivalObject>> festivalChild;

    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Map<String,Object> data = ParseDataCollector.collectFestivalData(intent.getStringExtra("eventId"), intent.getStringExtra("place"));
        festivalGroup = (List<String>)data.get("festivalGroup");
        festivalChild = (Map<String, List<FestivalObject>>)data.get("festivalChild");

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(fragmentAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColorResource(android.R.color.holo_purple);

    }

    public void likeHandler (View v) {
        FestivalObject item = (FestivalObject)v.getTag();
        SQLiteUtil.getInstence(this).addFavoriteId("Concert", item.getConcertId());
        item.setFavorite(!item.isFavorite());
        Utils.setFavoriteImage((ImageView) v, item.isFavorite());
        fragmentAdapter.refreshView();
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
                fragmentAdapter.filterInView(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fragmentAdapter.filterInView(s);
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

    public List<String> getFestivalGroup() {
        return festivalGroup;
    }

    public Map<String, List<FestivalObject>> getFestivalChild() {
        return festivalChild;
    }
}
