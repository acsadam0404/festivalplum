package hu.festivalplum.festival;


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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.festival.adapter.FragmentAdapter;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.LanguageDialog;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;


public class FestivalActivity extends FragmentActivity {

    private List<String> festivalGroup;
    private Map<String, List<FestivalObject>> festivalChild;

    private String info;
    private String map;

    protected String eventId;
    protected String place;
    private Boolean isFestival;
    protected Map<String,Object> data;

    private FragmentAdapter fragmentAdapter;
    private PagerSlidingTabStrip tabs;

    public static boolean modFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        place = intent.getStringExtra("place");
        isFestival = intent.getBooleanExtra("isFestival", true);

        info = intent.getStringExtra("info");
        map = intent.getStringExtra("map");

        getActionBar().setTitle(place);

        initFestival();
        festivalGroup = (List<String>)data.get("festivalGroup");
        festivalChild = (Map<String, List<FestivalObject>>)data.get("festivalChild");

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this, map != null);

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(fragmentAdapter);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);

        LinearLayout layout =  (LinearLayout)tabs.getChildAt(0);
        TextView view = (TextView)layout.getChildAt(0);
        view.setTextColor(getResources().getColor(android.R.color.holo_purple));

        tabs.delegatePageListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                LinearLayout layout =  (LinearLayout)tabs.getChildAt(0);
                int tabNum = layout.getChildCount();

                for (int i = 0; i < tabNum; i++) {
                    TextView view = (TextView)layout.getChildAt(i);
                    view.setTextColor(0xFF666666);
                }
                TextView view = (TextView)layout.getChildAt(position);
                view.setTextColor(FestivalActivity.this.getResources().getColor(android.R.color.holo_purple));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        };

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    protected void initFestival(){
        data = ParseDataCollector.collectFestivalData(this, eventId, place, false, Utils.getLanguageCode(this));
    }

    public void likeHandler (final View v) {
        FestivalObject item = (FestivalObject)v.getTag();
        SQLiteUtil.getInstence(FestivalActivity.this).addFavoriteId("Concert", item.getConcertId());
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
                break;
            case R.id.action_history:
                i = new Intent(this, HistoryActivity.class);
                this.startActivity(i);
                break;
            case R.id.action_language:
                new LanguageDialog(this).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        if(modFavorite){
            fragmentAdapter.refreshView();
            modFavorite = false;
        }
        super.onResume();
    }

    public String getInfo() {
        return info;
    }

    public String getMap() {
        return map;
    }

    public List<String> getFestivalGroup() {
        return festivalGroup;
    }

    public Map<String, List<FestivalObject>> getFestivalChild() {
        return festivalChild;
    }

    public Boolean isFestival() {
        return isFestival;
    }
}
