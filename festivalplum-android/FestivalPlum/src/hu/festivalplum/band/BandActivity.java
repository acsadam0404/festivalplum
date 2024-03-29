package hu.festivalplum.band;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.band.adapter.FragmentAdapter;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.LanguageDialog;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.31..
 */
public class BandActivity extends FragmentActivity {


    private String info;

    private FragmentAdapter fragmentAdapter;
    private PagerSlidingTabStrip tabs;

    private List<FestivalObject> concertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String bandId = intent.getStringExtra("bandId");
        info = intent.getStringExtra("info");
        String name = intent.getStringExtra("name");
        getActionBar().setTitle(name);
        concertList = ParseDataCollector.collectBandConcerts(this, bandId, Utils.getLanguageCode(this));

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);

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
                view.setTextColor(BandActivity.this.getResources().getColor(android.R.color.holo_purple));
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

    public void likeHandler (final View v) {
        FestivalObject item = (FestivalObject)v.getTag();
        SQLiteUtil.getInstence(BandActivity.this).addFavoriteId("Concert", item.getConcertId());
        item.setFavorite(!item.isFavorite());
        Utils.setFavoriteImage((ImageView) v, item.isFavorite());
        fragmentAdapter.notifyDataSetChanged();
    }

    public String getInfo() {
        return info;
    }

    public List<FestivalObject> getConcertList() {
        return concertList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.band_menu, menu);
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
}
