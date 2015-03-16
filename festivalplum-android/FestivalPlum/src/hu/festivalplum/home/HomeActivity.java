package hu.festivalplum.home;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import hu.festivalplum.R;

public class HomeActivity extends ActionBarActivity {
    private TabHost tabHost;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        final TabWidget tabWidget = tabHost.getTabWidget();
        tabWidget.removeAllViews();

        addTab(new NameView(this));
        addTab(new TimeView(this));
        addTab(new SettlementView(this));
        addTab(new ArtistView(this));

        tabHost.setCurrentTab(1);
    }

    private void addTab(final HomeViewTab tab) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tab.getName());
        tabSpec.setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String tag) {
                return (View) tab;
            }
        });

        tabSpec.setIndicator(tab.getName());
        tabHost.addTab(tabSpec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
