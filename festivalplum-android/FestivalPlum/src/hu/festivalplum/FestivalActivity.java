package hu.festivalplum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.festivalplum.festival.FestivalObject;
import hu.festivalplum.festival.FestivalView;


public class FestivalActivity extends Activity {

    private List<String> headerTitles;
    private Map<String, List<FestivalObject>> childTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_festival);

        Intent intent = getIntent();
        collectData(intent.getStringExtra("eventId"), intent.getStringExtra("place"));
        setContentView(new FestivalView(this));

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

    private void collectData(String eventId, final String place){
        headerTitles = new ArrayList<String>();
        childTitles = new HashMap<String,List<FestivalObject>>();

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Event");
        q.whereEqualTo("objectId", eventId);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
        query.include("band");
        query.include("stage");
        query.whereMatchesQuery("event", q);
        query.orderByAscending("startDate");
        try {
            List<ParseObject> result = query.find();
            Calendar cal = Calendar.getInstance();
            if(result.size() > 0){
                cal.setTime(result.get(0).getDate("startDate"));
                int minDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy.MM.dd");
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                for(int i = 0; i < result.size(); i++) {
                    ParseObject concert = result.get(i);
                    ParseObject band = concert.getParseObject("band");
                    ParseObject stage = concert.getParseObject("stage");


                    Date startDate = concert.getDate("startDate");
                    Date toDate = concert.getDate("toDate");

                    cal.setTime(startDate);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int min = cal.get(Calendar.MINUTE);

                    int festDay = cal.get(Calendar.DAY_OF_YEAR) - minDayOfYear + 1;
                    String title = sdfDate.format(startDate) + " " + place + " (" + festDay + ". nap)";
                    String time = sdfTime.format(startDate);
                    ParseFile imageFile = (ParseFile)band.get("image");
                    byte[] bandImg = imageFile.getData();

                    String stageName = stage.getString("name");
                    String bandName = band.getString("name");

                    FestivalObject festivalObject = new FestivalObject();
                    festivalObject.setStartDate(startDate);
                    festivalObject.setBandName(bandName);
                    festivalObject.setImage(bandImg);
                    festivalObject.setStageName(stageName);
                    festivalObject.setToDate(toDate);
                    festivalObject.setTime(time);

                    if(!childTitles.containsKey(title)){
                        headerTitles.add(title);
                        List<FestivalObject> list = new ArrayList<FestivalObject>();
                        list.add(festivalObject);
                        childTitles.put(title, list);
                    }else{
                        childTitles.get(title).add(festivalObject);
                    }

                }
            }
        } catch (ParseException e){
            int i = 0;
            i++;
        }
    }

    public List<String> getHeaderTitles() {
        return headerTitles;
    }

    public Map<String, List<FestivalObject>> getChildTitles() {
        return childTitles;
    }
}
