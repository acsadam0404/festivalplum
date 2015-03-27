package hu.festivalplum.home;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;


import com.astuetz.PagerSlidingTabStrip;

import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.favorite.FavoriteActivity;
import hu.festivalplum.history.HistoryActivity;
import hu.festivalplum.home.adapter.FragmentAdapter;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

public class HomeActivity extends FragmentActivity {

    private List<String> timeGroup;
    private Map<String, List<HomeObject>> timeChild;

    private List<String> nameGroup;
    private Map<String, List<HomeObject>> nameChild;

    private List<String> cityGroup;
    private Map<String, List<HomeObject>> cityChild;

    private FragmentAdapter fragmentAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Map<String, Object> data =  ParseDataCollector.collectHomeData();
        timeGroup = (List<String>)data.get("timeGroup");
        timeChild = (Map<String, List<HomeObject>>)data.get("timeChild");
        nameGroup = (List<String>)data.get("nameGroup");
        nameChild = (Map<String, List<HomeObject>>)data.get("nameChild");
        cityGroup = (List<String>)data.get("cityGroup");
        cityChild = (Map<String, List<HomeObject>>)data.get("cityChild");
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(fragmentAdapter);
        p.setCurrentItem(1);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColorResource(android.R.color.holo_purple);

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
                break;
            case R.id.action_history:
                i = new Intent(this, HistoryActivity.class);
                this.startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<String> getTimeGroup() {
        return timeGroup;
    }

    public Map<String, List<HomeObject>> getTimeChild() {
        return timeChild;
    }

    public List<String> getNameGroup() {
        return nameGroup;
    }

    public Map<String, List<HomeObject>> getNameChild() {
        return nameChild;
    }

    public List<String> getCityGroup() {
        return cityGroup;
    }

    public Map<String, List<HomeObject>> getCityChild() {
        return cityChild;
    }

}
