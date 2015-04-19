package hu.festivalplum.utils;

import android.content.Context;

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

import hu.festivalplum.model.BandObject;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.model.HomeObject;

/**
 * Created by viktor on 2015.03.22..
 */
public class ParseDataCollector {

    // Jelenleg ha hiányzik egy megjelenítendő érték akkor adott Event kimarad a listából
    public static Map<String, Object> collectHomeData(Context context){
        Map<String, Object> ret = new HashMap<>();

        List<String> timeGroup = new ArrayList<String>();
        Map<String, List<HomeObject>> timeChild = new HashMap<String,List<HomeObject>>();
        List<String> nameGroup = new ArrayList<String>();
        Map<String, List<HomeObject>> nameChild = new HashMap<String,List<HomeObject>>();
        List<String> cityGroup = new ArrayList<String>();
        Map<String, List<HomeObject>> cityChild = new HashMap<String,List<HomeObject>>();

        Date date = new Date();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.include("place");
        query.whereGreaterThanOrEqualTo("endDate", date);
        query.orderByAscending("startDate");

        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject event = result.get(i);
                ParseObject place = event.getParseObject("place");
                if(place == null)
                    continue;
                String cityName = place.getString("city");
                if(cityName == null)
                    continue;
                Date startDate = event.getDate("startDate");
                if(startDate == null)
                    continue;
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                Integer year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                String title = "'" + year.toString().substring(2,4) + " " + Utils.getMonthName(context, month);
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
                Boolean isFestival = event.getBoolean("isFestival");
                if(isFestival == null)
                    continue;
                HomeObject homeObject = new HomeObject();
                homeObject.setCityName(cityName);
                homeObject.setStartDate(startDate);
                homeObject.setEndDate(endDate);
                homeObject.setEventId(eventId);
                homeObject.setPlaceImg(placeImg);
                homeObject.setPlaceName(placeName);
                homeObject.setFestival(isFestival);
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

    public static Map<String, Object> collectHistoryData(Context context){
        Map<String, Object> ret = new HashMap<>();

        List<String> timeGroup = new ArrayList<String>();
        Map<String, List<HomeObject>> timeChild = new HashMap<String,List<HomeObject>>();

        Date date = new Date();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.include("place");
        query.whereLessThanOrEqualTo("startDate", date);
        query.orderByAscending("startDate");

        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject event = result.get(i);
                ParseObject place = event.getParseObject("place");
                if(place == null)
                    continue;
                String cityName = place.getString("city");
                if(cityName == null)
                    continue;
                Date startDate = event.getDate("startDate");
                if(startDate == null)
                    continue;
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                Integer year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                String title = "'" + year.toString().substring(2,4) + " " + Utils.getMonthName(context, month);
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

            }

            ret.put("timeGroup", timeGroup);
            ret.put("timeChild", timeChild);

        } catch (ParseException e){
            //
        }

        return ret;
    }

    public static  Map<String, Object> collectBandData(){
        Map<String, Object> ret = new HashMap<>();
        Map<String, BandObject> bandObjectMap = new HashMap<>();
        List<String> distinctHelper = new ArrayList<>();
        Date date = new Date();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
        query.include("band");
        query.whereGreaterThan("startDate", date);


        try {
            List<ParseObject> result = query.find();
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    ParseObject concert = result.get(i);
                    ParseObject band = concert.getParseObject("band");
                    if(band == null)
                        continue;
                    String bandId = band.getObjectId();
                    String name = band.getString("name");
                    String style = band.getString("style");
                    String info = band.getString("description");
                    ParseFile imageFile = (ParseFile) band.get("image");
                    if(imageFile == null)
                        continue;
                    byte[] bandImg = imageFile.getData();
                    BandObject bandObject = new BandObject();
                    if(!distinctHelper.contains(name)) {
                        bandObject.setName(name);
                        bandObject.setStyle(style);
                        bandObject.setBandImg(bandImg);
                        bandObject.setBandId(bandId);
                        bandObject.setHtmlInfo(info);
                        distinctHelper.add(name);
                        bandObjectMap.put(name,bandObject);
                    }
                }
            }
        }catch (Exception e){
            //
        }
        ret.put("list",distinctHelper);
        ret.put("map",bandObjectMap);
        return ret;
    }

    public static List<FestivalObject> collectBandConcerts(Context context, String bandId){
        List<FestivalObject> ret = new ArrayList<>();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Date endDate = null;
        try {
            endDate = Utils.getSdf(context, Utils.sdf).parse(year + "." + month + "." + day + ".06:00");
        }catch (Exception e){
            //
        }

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Band");
        q.whereEqualTo("objectId", bandId);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
        query.include("band");
        query.include("stage");
        query.include("event.place");
        query.whereMatchesQuery("band", q);
        if(endDate != null)
            query.whereGreaterThan("toDate", endDate);
        query.orderByAscending("startDate");

        try {
            List<ParseObject> result = query.find();
            if (result.size() > 0) {
                cal.setTime(result.get(0).getDate("startDate"));
                int minDayOfYear = cal.get(Calendar.DAY_OF_YEAR);

                for (int i = 0; i < result.size(); i++) {
                    ParseObject concert = result.get(i);
                    String concertId = concert.getObjectId();
                    ParseObject band = concert.getParseObject("band");
                    if(band == null)
                        continue;
                    ParseObject stage = concert.getParseObject("stage");
                    if(stage == null)
                        continue;
                    ParseObject event = concert.getParseObject("event");
                    if(event == null)
                        continue;
                    ParseObject place = event.getParseObject("place");
                    if(place == null)
                        continue;
                    Date startDate = concert.getDate("startDate");
                    if(startDate == null)
                        continue;
                    Date toDate = concert.getDate("toDate");
                    if(toDate == null)
                        continue;
                    cal.setTime(startDate);

                    int festDay = cal.get(Calendar.DAY_OF_YEAR) - minDayOfYear + 1;
                    //String title = Utils.sdfDate.format(startDate) + /*" " + place +*/ " (" + festDay + ". nap)";
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
                    String bandInfo = band.getString("info");
                    String placeName = place.getString("name");
                    if(placeName == null)
                        continue;

                    FestivalObject festivalObject = new FestivalObject();
                    festivalObject.setStartDate(startDate);
                    festivalObject.setBandName(bandName);
                    festivalObject.setImage(bandImg);
                    festivalObject.setStageName(stageName);
                    festivalObject.setToDate(toDate);
                    festivalObject.setConcertId(concertId);
                    festivalObject.setBandHtmlInfo(bandInfo);
                    festivalObject.setPlaceName(placeName);

                   ret.add(festivalObject);

                }
            }

        } catch (ParseException e) {
            //
        }

        return ret;
    }

    public static Map<String, Object> collectFestivalData(Context context, String eventId, final String place, boolean history) {
        Map<String, Object> ret = new HashMap<>();

        List<String> festivalGroup = new ArrayList<String>();
        Map<String, List<FestivalObject>> festivalChild = new HashMap<String, List<FestivalObject>>();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -2);
        Date newDate = cal.getTime();

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Event");
        q.whereEqualTo("objectId", eventId);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
        query.include("band");
        query.include("stage");
        query.include("event");
        query.whereMatchesQuery("event", q);
        if(newDate != null) {
            if(!history)
                query.whereGreaterThan("startDate", newDate);
            else
                query.whereLessThan("startDate", date);
        }
        query.orderByAscending("startDate");
        try {
            List<ParseObject> result = query.find();
            if (result.size() > 0) {
                cal.setTime(result.get(0).getParseObject("event").getDate("startDate"));
                int minDayOfYear = cal.get(Calendar.DAY_OF_YEAR);

                for (int i = 0; i < result.size(); i++) {
                    ParseObject concert = result.get(i);
                    String concertId = concert.getObjectId();
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
                    Date titleStartDate = startDate;
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    if(hour < 6){
                        cal.add(Calendar.DAY_OF_MONTH, -1);
                        titleStartDate = cal.getTime();
                    }

                    int festDay = cal.get(Calendar.DAY_OF_YEAR) - minDayOfYear + 1;
                    String title = Utils.getSdf(context, Utils.sdfMMdd).format(titleStartDate) + " (" + festDay + ". nap)";
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
                    String bandInfo = band.getString("description");

                    FestivalObject festivalObject = new FestivalObject();
                    festivalObject.setStartDate(startDate);
                    festivalObject.setBandName(bandName);
                    festivalObject.setImage(bandImg);
                    festivalObject.setStageName(stageName);
                    festivalObject.setToDate(toDate);
                    festivalObject.setConcertId(concertId);
                    festivalObject.setBandHtmlInfo(bandInfo);
                    festivalObject.setPlaceName(place);

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

    public static List<FestivalObject> collectFavoriteData(List<String> concertIds){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
        query.whereContainedIn("objectId", concertIds);
        query.include("band");
        query.include("stage");
        query.include("event.place");
        //query.whereGreaterThan("toDate", new Date());
        query.orderByAscending("startDate");

        return getFestivalList(query);
    }

    private static List<FestivalObject> getFestivalList(ParseQuery<ParseObject> query){
        List<FestivalObject> ret = new ArrayList<>();
        try {
            List<ParseObject> result = query.find();
            Calendar cal = Calendar.getInstance();
            if (result.size() > 0) {
                cal.setTime(result.get(0).getDate("startDate"));
                int minDayOfYear = cal.get(Calendar.DAY_OF_YEAR);

                for (int i = 0; i < result.size(); i++) {
                    ParseObject concert = result.get(i);
                    String concertId = concert.getObjectId();
                    ParseObject band = concert.getParseObject("band");
                    if (band == null)
                        continue;
                    ParseObject stage = concert.getParseObject("stage");
                    if (stage == null)
                        continue;
                    ParseObject event = concert.getParseObject("event");
                    if(event == null)
                        continue;
                    ParseObject place = event.getParseObject("place");
                    if(place == null)
                        continue;
                    Date startDate = concert.getDate("startDate");
                    if (startDate == null)
                        continue;
                    Date toDate = concert.getDate("toDate");
                    if (toDate == null)
                        continue;
                    cal.setTime(startDate);

                    int festDay = cal.get(Calendar.DAY_OF_YEAR) - minDayOfYear + 1;

                    ParseFile imageFile = (ParseFile) band.get("image");
                    if (imageFile == null)
                        continue;
                    byte[] bandImg = imageFile.getData();

                    String stageName = stage.getString("name");
                    if (stageName == null)
                        continue;
                    String bandName = band.getString("name");
                    if (bandName == null)
                        continue;
                    String placeName = place.getString("name");
                    if(placeName == null)
                        continue;
                    FestivalObject festivalObject = new FestivalObject();
                    festivalObject.setStartDate(startDate);
                    festivalObject.setBandName(bandName);
                    festivalObject.setImage(bandImg);
                    festivalObject.setStageName(stageName);
                    festivalObject.setToDate(toDate);
                    festivalObject.setConcertId(concertId);
                    festivalObject.setPlaceName(placeName);
                    ret.add(festivalObject);
                }
            }
        }catch (Exception e){
            //
        }
        return ret;
    }

    public static List<HomeObject> collectSearchData(String name){
        ParseQuery<ParseObject> q = ParseQuery.getQuery("Place");
        q.whereMatches("name", name);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.include("place");
        query.whereMatchesQuery("place", q);

        return getHomeList(query);
    }

    private static List<HomeObject> getHomeList(ParseQuery<ParseObject> query){
        List<HomeObject> ret = new ArrayList<>();
        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject event = result.get(i);
                ParseObject place = event.getParseObject("place");
                if (place == null)
                    continue;
                String cityName = place.getString("city");
                if (cityName == null)
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
