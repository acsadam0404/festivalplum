package hu.festivalplum.favorite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.LanguageDialog;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

public class FavoriteActivity extends Activity {

    private FavoriteViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView v = (ListView) findViewById(R.id.listView);

        List<String> concertIds = SQLiteUtil.getInstence(this).getFavoriteIds("Concert");
        List<FestivalObject> items = ParseDataCollector.collectFavoriteData(concertIds, Utils.getLanguageCode(this));

        mAdapter = new FavoriteViewAdapter(this, items);
        v.setAdapter(mAdapter);

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
                        for (FestivalObject o : mAdapter.getAllSelectedItem()){
                            SQLiteUtil.getInstence(FavoriteActivity.this).removeFavoriteId("Concert", o.getConcertId(), true);
                        }
                        break;
                }
                FestivalActivity.modFavorite = true;
                mAdapter.notifyDataSetChanged();
                actionMode.finish();
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                mAdapter.clearSelection();
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void likeHandler (final View v) {
        final FestivalObject item = (FestivalObject)v.getTag();
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_delete_favorite_title)
                .setMessage(R.string.dialog_delete_favorite_msg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteUtil.getInstence(FavoriteActivity.this).addFavoriteId("Concert", item.getConcertId());
                        item.setFavorite(!item.isFavorite());
                        Utils.setFavoriteImage((ImageView) v, item.isFavorite());
                        FestivalActivity.modFavorite = true;
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
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
