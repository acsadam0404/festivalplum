package hu.festivalplum.favorite;

import android.app.Activity;
import android.os.Bundle;

public class FavoriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FavoriteView(this));
    }
}
