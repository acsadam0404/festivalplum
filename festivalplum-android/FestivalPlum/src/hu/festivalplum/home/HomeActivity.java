package hu.festivalplum.home;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import hu.festivalplum.R;
import hu.festivalplum.favorite.FavoriteActivity;
import hu.festivalplum.history.HistoryActivity;
import hu.festivalplum.home.adapter.FragmentAdapter;
import hu.festivalplum.utils.LanguageDialog;

public class HomeActivity extends FragmentActivity {

    private PagerSlidingTabStrip tabs;
    private FragmentAdapter fragmentAdapter;
    private SearchView searchView;
    private boolean isList;
    private boolean highPriority;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        isList = getIntent().getBooleanExtra("isList", true);
        highPriority = getIntent().getBooleanExtra("highPriority", true);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(fragmentAdapter);
        //p.setCurrentItem(1);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);
        LinearLayout layout =  (LinearLayout)tabs.getChildAt(0);
        TextView view = (TextView)layout.getChildAt(0);
        view.setTextColor(HomeActivity.this.getResources().getColor(android.R.color.holo_purple));

        tabs.delegatePageListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if(!HomeActivity.this.searchView.isIconified()){
                    HomeActivity.this.searchView.setIconified(true);
                }

                LinearLayout layout =  (LinearLayout)tabs.getChildAt(0);
                int tabNum = layout.getChildCount();

                for (int i = 0; i < tabNum; i++) {
                    TextView view = (TextView)layout.getChildAt(i);
                    view.setTextColor(0xFF666666);
                }
                TextView view = (TextView)layout.getChildAt(position);
                view.setTextColor(HomeActivity.this.getResources().getColor(android.R.color.holo_purple));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_sett);
        if(!isList){
            item.setIcon(R.drawable.fp_ikon_list);
        }else{
            item.setIcon(R.drawable.fp_ikon_list_2);
        }

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
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
                fragmentAdapter.expendGroup();
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
            case R.id.action_sett:
                reload(!isList, highPriority);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isList(){
        return isList;
    }

    public boolean isHighPriority(){
        return highPriority;
    }

    public void reload(boolean isList, boolean highPriority){
        Intent intent = getIntent();
        intent.putExtra("isList", isList);
        intent.putExtra("highPriority", highPriority);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
