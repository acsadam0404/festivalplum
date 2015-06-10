package hu.festivalplum.history;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import hu.festivalplum.R;
import hu.festivalplum.utils.LanguageDialog;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.29..
 */
public class FestivalActivity extends hu.festivalplum.festival.FestivalActivity {

    @Override
    public void likeHandler (View v) {
        //
    }

    @Override
    protected void initFestival(){
        data = ParseDataCollector.collectFestivalData(this, eventId, place, true, Utils.getLanguageCode(this));
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
