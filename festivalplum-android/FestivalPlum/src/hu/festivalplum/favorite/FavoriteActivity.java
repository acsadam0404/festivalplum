package hu.festivalplum.favorite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import hu.festivalplum.MapActivity;
import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;

public class FavoriteActivity extends Activity {

    private FavoriteViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView v = (ListView) findViewById(R.id.listView);

        List<String> eventIds = SQLiteUtil.getInstence(this).getFavoriteIds("Event");
        List<HomeObject> items = ParseDataCollector.collectFavoriteData(eventIds);

        mAdapter = new FavoriteViewAdapter(this, items);
        v.setAdapter(mAdapter);

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavoriteActivity.this, FestivalActivity.class);
                HomeObject object = (HomeObject) adapterView.getAdapter().getItem(i);
                intent.putExtra("eventId", object.getEventId());
                intent.putExtra("place", object.getPlaceName());
                FavoriteActivity.this.startActivity(intent);
            }
        });

        v.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        v.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            private int nr = 0;
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    nr++;
                    mAdapter.setNewSelection(position, checked);
                } else {
                    nr--;
                    mAdapter.removeSelection(position);
                }
                mode.setTitle(nr + " " + getString(R.string.selected));
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                nr = 0;
                MenuInflater inflater = FavoriteActivity.this.getMenuInflater();
                inflater.inflate(R.menu.contextual_favorite, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_delete:
                        for (HomeObject o : mAdapter.getAllSelectedItem()){
                            SQLiteUtil.getInstence(FavoriteActivity.this).removeFavoriteId("Event", o.getEventId(), true);
                        }
                        break;
                }
                HomeActivity.modFavorite = true;
                mAdapter.notifyDataSetChanged();
                actionMode.finish();
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                mAdapter.clearSelection();
            }
        });


    }

    public void mapHandler (View v) {
        HomeObject item = (HomeObject)v.getTag();
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("eventId", item.getEventId());
        startActivity(intent);
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }
}
