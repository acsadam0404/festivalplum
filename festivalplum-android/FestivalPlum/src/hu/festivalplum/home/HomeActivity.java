package hu.festivalplum.home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import hu.festivalplum.R;

public class HomeActivity extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
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
