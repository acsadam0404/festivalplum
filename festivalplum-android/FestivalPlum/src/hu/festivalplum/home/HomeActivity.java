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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;


import com.astuetz.PagerSlidingTabStrip;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.StarterActivity;
import hu.festivalplum.favorite.FavoriteActivity;
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

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);
    }

    public void likeHandler (View v) {
        HomeObject item = (HomeObject)v.getTag();
        SQLiteUtil.getInstence(this).addFavoriteId("Event", item.getEventId());
        item.setFavorite(!item.isFavorite());
        Utils.setFavoriteImage((ImageView)v,item.isFavorite());
        fragmentAdapter.refreshView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
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
                onSearchRequested();
                break;
            case R.id.action_favourite:
                i = new Intent(this, FavoriteActivity.class);
                this.startActivity(i);

                break;
            case R.id.action_starter:
                i = new Intent(this, StarterActivity.class);
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
