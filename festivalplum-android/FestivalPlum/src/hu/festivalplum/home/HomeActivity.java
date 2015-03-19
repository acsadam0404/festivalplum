package hu.festivalplum.home;


import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


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

public class HomeActivity extends FragmentActivity {

    private List<String> timeHeaderTitles;
    private Map<String, List<HomeObject>> timeChildTitles;

    private List<String> nameHeaderTitles;
    private Map<String, List<HomeObject>> nameChildTitles;

    private List<String> cityHeaderTitles;
    private Map<String, List<HomeObject>> cityChildTitles;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        collectData();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);
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

    private void collectData(){
        timeHeaderTitles = new ArrayList<String>();
        timeChildTitles = new HashMap<String,List<HomeObject>>();

        nameHeaderTitles = new ArrayList<String>();
        nameChildTitles = new HashMap<String,List<HomeObject>>();

        cityHeaderTitles = new ArrayList<String>();
        cityChildTitles = new HashMap<String,List<HomeObject>>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.include("place");
        query.include("place.address");

        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject event = result.get(i);
                ParseObject place = event.getParseObject("place");
                ParseObject city = place.getParseObject("address");
                Date startDate = event.getDate("startDate");
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                String title = year + ". " + HomeUtil.getMonthName(month);
                String eventId = event.getObjectId();
                Date endDate = event.getDate("startDate");

                ParseFile imageFile = (ParseFile)place.get("image");
                byte[] placeImg = imageFile.getData();
                //byte[] placeImg = place.getBytes("image");
                String placeName = place.getString("name");
                String cityName = city.getString("city");
                HomeObject homeObject = new HomeObject();
                homeObject.setCityName(cityName);
                homeObject.setStartDate(startDate);
                homeObject.setEndDate(endDate);
                homeObject.setEventId(eventId);
                homeObject.setPlaceImg(placeImg);
                homeObject.setPlaceName(placeName);

                if(!timeChildTitles.containsKey(title)){
                    timeHeaderTitles.add(title);
                    List<HomeObject> list = new ArrayList<HomeObject>();
                    list.add(homeObject);
                    timeChildTitles.put(title, list);
                }else{
                    timeChildTitles.get(title).add(homeObject);
                }

                if(!nameChildTitles.containsKey(placeName)){
                    nameHeaderTitles.add(placeName);
                    List<HomeObject> list = new ArrayList<HomeObject>();
                    list.add(homeObject);
                    nameChildTitles.put(placeName, list);
                }else{
                    nameChildTitles.get(placeName).add(homeObject);
                }

                if(!cityChildTitles.containsKey(cityName)){
                    cityHeaderTitles.add(cityName);
                    List<HomeObject> list = new ArrayList<HomeObject>();
                    list.add(homeObject);
                    cityChildTitles.put(cityName, list);
                }else{
                    cityChildTitles.get(cityName).add(homeObject);
                }

            }
        } catch (ParseException e){
            //
        }
    }

    public List<String> getTimeHeaderTitles() {
        return timeHeaderTitles;
    }

    public Map<String, List<HomeObject>> getTimeChildTitles() {
        return timeChildTitles;
    }

    public List<String> getNameHeaderTitles() {
        return nameHeaderTitles;
    }

    public Map<String, List<HomeObject>> getNameChildTitles() {
        return nameChildTitles;
    }

    public List<String> getCityHeaderTitles() {
        return cityHeaderTitles;
    }

    public Map<String, List<HomeObject>> getCityChildTitles() {
        return cityChildTitles;
    }

}
