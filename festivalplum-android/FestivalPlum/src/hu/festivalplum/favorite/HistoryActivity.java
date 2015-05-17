package hu.festivalplum.favorite;

import android.content.Intent;
import android.view.MenuItem;

import hu.festivalplum.R;
import hu.festivalplum.utils.LanguageDialog;

/**
 * Created by viktor on 2015.05.17..
 */
public class HistoryActivity extends hu.festivalplum.history.HistoryActivity {
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
            case R.id.action_language:
                new LanguageDialog(this).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
