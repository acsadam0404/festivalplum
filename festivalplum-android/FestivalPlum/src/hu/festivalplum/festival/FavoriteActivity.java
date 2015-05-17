package hu.festivalplum.festival;

import android.content.Intent;
import android.view.MenuItem;

import hu.festivalplum.R;
import hu.festivalplum.utils.LanguageDialog;

/**
 * Created by viktor on 2015.04.17..
 */
public class FavoriteActivity extends hu.festivalplum.favorite.FavoriteActivity {
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
