package hu.festivalplum.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.astuetz.PagerSlidingTabStrip;

import hu.festivalplum.R;

public class HomeActivity extends ActionBarActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        switch (id){
            case R.id.action_settings:

                break;
            case R.id.action_search:

                break;
            case R.id.action_favourite:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
