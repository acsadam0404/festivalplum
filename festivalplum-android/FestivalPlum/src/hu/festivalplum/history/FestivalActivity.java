package hu.festivalplum.history;

import android.view.View;

import java.util.Map;

import hu.festivalplum.utils.ParseDataCollector;

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
        data = ParseDataCollector.collectFestivalData(eventId, place, true);
    }

}
