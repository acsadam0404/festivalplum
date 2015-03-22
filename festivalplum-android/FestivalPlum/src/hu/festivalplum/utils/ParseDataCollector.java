package hu.festivalplum.utils;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.model.HomeObject;

/**
 * Created by viktor on 2015.03.22..
 */
public class ParseDataCollector {

    // Jelenleg ha hiányzik egy megjelenítendő érték akkor adott Event kimarad a listából
    public static Map<String, Object> collectHomeData(){
        Map<String, Object> ret = new HashMap<>();

        List<String> timeGroup = new ArrayList<String>();
        Map<String, List<HomeObject>> timeChild = new HashMap<String,List<HomeObject>>();
        List<String> nameGroup = new ArrayList<String>();
        Map<String, List<HomeObject>> nameChild = new HashMap<String,List<HomeObject>>();
        List<String> cityGroup = new ArrayList<String>();
        Map<String, List<HomeObject>> cityChild = new HashMap<String,List<HomeObject>>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.include("place");
        query.include("place.address");

        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject event = result.get(i);
                ParseObject place = event.getParseObject("place");
                if(place == null)
                    continue;
                ParseObject city = place.getParseObject("address");
                if(city == null)
                    continue;
                Date startDate = event.getDate("startDate");
                if(startDate == null)
                    continue;
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                String title = year + ". " + Utils.getMonthName(month);
                String eventId = event.getObjectId();
                Date endDate = event.getDate("endDate");
                if(endDate == null)
                    continue;
                ParseFile imageFile = (ParseFile)place.get("image");
                if(imageFile == null)
                    continue;
                byte[] placeImg = imageFile.getData();
                String placeName = place.getString("name");
                if(placeName == null)
                    continue;
                String cityName = city.getString("city");
                if(cityName == null)
                    continue;
                HomeObject homeObject = new HomeObject();
                homeObject.setCityName(cityName);
                homeObject.setStartDate(startDate);
                homeObject.setEndDate(endDate);
                homeObject.setEventId(eventId);
                homeObject.setPlaceImg(placeImg);
                homeObject.setPlaceName(placeName);

                if(!timeChild.containsKey(title)){
                    timeGroup.add(title);
                    List<HomeObject> list = new ArrayList<HomeObject>();
                    list.add(homeObject);
                    timeChild.put(title, list);
                }else{
                    timeChild.get(title).add(homeObject);
                }

                if(!nameChild.containsKey(placeName)){
                    nameGroup.add(placeName);
                    List<HomeObject> list = new ArrayList<HomeObject>();
                    list.add(homeObject);
                    nameChild.put(placeName, list);
                }else{
                    nameChild.get(placeName).add(homeObject);
                }

                if(!cityChild.containsKey(cityName)){
                    cityGroup.add(cityName);
                    List<HomeObject> list = new ArrayList<HomeObject>();
                    list.add(homeObject);
                    cityChild.put(cityName, list);
                }else{
                    cityChild.get(cityName).add(homeObject);
                }

            }

            ret.put("timeGroup", timeGroup);
            ret.put("timeChild", timeChild);
            ret.put("cityGroup", cityGroup);
            ret.put("cityChild", cityChild);
            ret.put("nameGroup", nameGroup);
            ret.put("nameChild", nameChild);


        } catch (ParseException e){
            //
        }

        return ret;
    }

    public static Map<String, Object> collectFestivalData(String eventId, final String place) {
        Map<String, Object> ret = new HashMap<>();

        List<String> festivalGroup = new ArrayList<String>();
        Map<String, List<FestivalObject>> festivalChild = new HashMap<String, List<FestivalObject>>();

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
            if (result.size() > 0) {
                cal.setTime(result.get(0).getDate("startDate"));
                int minDayOfYear = cal.get(Calendar.DAY_OF_YEAR);

                for (int i = 0; i < result.size(); i++) {
                    ParseObject concert = result.get(i);
                    ParseObject band = concert.getParseObject("band");
                    if(band == null)
                        continue;
                    ParseObject stage = concert.getParseObject("stage");
                    if(stage == null)
                        continue;
                    Date startDate = concert.getDate("startDate");
                    if(startDate == null)
                        continue;
                    Date toDate = concert.getDate("toDate");
                    if(toDate == null)
                        continue;
                    cal.setTime(startDate);

                    int festDay = cal.get(Calendar.DAY_OF_YEAR) - minDayOfYear + 1;
                    String title = Utils.sdfDate.format(startDate) + " " + place + " (" + festDay + ". nap)";
                    ParseFile imageFile = (ParseFile) band.get("image");
                    if(imageFile == null)
                        continue;
                    byte[] bandImg = imageFile.getData();

                    String stageName = stage.getString("name");
                    if(stageName == null)
                        continue;
                    String bandName = band.getString("name");
                    if(bandName == null)
                        continue;
                    FestivalObject festivalObject = new FestivalObject();
                    festivalObject.setStartDate(startDate);
                    festivalObject.setBandName(bandName);
                    festivalObject.setImage(bandImg);
                    festivalObject.setStageName(stageName);
                    festivalObject.setToDate(toDate);

                    if (!festivalChild.containsKey(title)) {
                        festivalGroup.add(title);
                        List<FestivalObject> list = new ArrayList<FestivalObject>();
                        list.add(festivalObject);
                        festivalChild.put(title, list);
                    } else {
                        festivalChild.get(title).add(festivalObject);
                    }

                }
            }
            ret.put("festivalGroup", festivalGroup);
            ret.put("festivalChild", festivalChild);
        } catch (ParseException e) {
            //
        }
        return ret;
    }

    public static List<HomeObject> collectFavoriteData(List<String> eventIds){
        List<HomeObject> ret = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.whereContainedIn("objectId", eventIds);
        query.include("place");
        query.include("place.address");

        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject event = result.get(i);
                ParseObject place = event.getParseObject("place");
                if (place == null)
                    continue;
                ParseObject city = place.getParseObject("address");
                if (city == null)
                    continue;
                Date startDate = event.getDate("startDate");
                if (startDate == null)
                    continue;

                String eventId = event.getObjectId();
                Date endDate = event.getDate("endDate");
                if (endDate == null)
                    continue;
                ParseFile imageFile = (ParseFile) place.get("image");
                if (imageFile == null)
                    continue;
                byte[] placeImg = imageFile.getData();
                String placeName = place.getString("name");
                if (placeName == null)
                    continue;
                String cityName = city.getString("city");
                if (cityName == null)
                    continue;
                HomeObject homeObject = new HomeObject();
                homeObject.setCityName(cityName);
                homeObject.setStartDate(startDate);
                homeObject.setEndDate(endDate);
                homeObject.setEventId(eventId);
                homeObject.setPlaceImg(placeImg);
                homeObject.setPlaceName(placeName);

                ret.add(homeObject);
            }

        } catch (ParseException e){
            e.printStackTrace();
        }

        return ret;
    }
}
