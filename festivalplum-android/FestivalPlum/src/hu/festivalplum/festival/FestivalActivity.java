package hu.festivalplum.festival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.ParseDataCollector;


public class FestivalActivity extends Activity {

    private List<String> festivalGroup;
    private Map<String, List<FestivalObject>> festivalChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        Map<String,Object> data = ParseDataCollector.collectFestivalData(intent.getStringExtra("eventId"), intent.getStringExtra("place"));
        festivalGroup = (List<String>)data.get("festivalGroup");
        festivalChild = (Map<String, List<FestivalObject>>)data.get("festivalChild");

        ExpandableListView v = new ExpandableListView(this);
        v.setAdapter(new FestivalViewAdapter(this, festivalGroup, festivalChild));

        setContentView(v);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_festival, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public List<String> getFestivalGroup() {
        return festivalGroup;
    }

    public Map<String, List<FestivalObject>> getFestivalChild() {
        return festivalChild;
    }
}
