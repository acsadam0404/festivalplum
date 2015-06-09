package hu.festivalplum;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import hu.festivalplum.model.BandObject;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.Utils;

public class FPApplication extends Application {

    private static final String FIRST_CITY = "Budapest";

    private static FPApplication application;

    //HOME
    private static List<String> timeGroup;
    private static Map<String, List<HomeObject>> timeChild;
    private static List<String> nameGroup;
    private static List<HomeObject> nameList;
    private static Map<String, List<HomeObject>> nameChild;
    private static List<String> cityGroup;
    private static Map<String, List<HomeObject>> cityChild;
    private static List<BandObject> bandData;
    private static Map<String,Object> bandMap;
    //HISTORY
    private static List<String> historyTimeGroup;
    private static Map<String, List<HomeObject>> historyTimeChild;

    public FPApplication(){

    }

    public static FPApplication getInstence(){
        if(application == null){
            application = new FPApplication();
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.setLanguage(this);
        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "EHBh2sz9yo6a99A5UMDjYBKPGWD9zpl35JhSZQG8", "lUXrxJ1xNJ7tQKVeE7dXt7kTP3bjECh8Wuestyjv");
        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().saveInBackground();
        ParseACL defaultACL = new ParseACL();
        //defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }

    public static void initParseData(Context context){

        bandMap = ParseDataCollector.collectBandData(Utils.getLanguageCode(context));
        Map<String, Object> data =  ParseDataCollector.collectHomeData(context, Utils.getLanguageCode(context));
        timeGroup = (List<String>)data.get("timeGroup");
        timeChild = (Map<String, List<HomeObject>>)data.get("timeChild");
        nameGroup = (List<String>)data.get("nameGroup");
        nameChild = (Map<String, List<HomeObject>>)data.get("nameChild");
        cityGroup = (List<String>)data.get("cityGroup");
        cityChild = (Map<String, List<HomeObject>>)data.get("cityChild");

        Map<String, Object> historyData =  ParseDataCollector.collectHistoryData(context);
        historyTimeGroup = (List<String>)historyData.get("timeGroup");
        historyTimeChild = (Map<String, List<HomeObject>>)historyData.get("timeChild");

        orderParseData();
    }



    private static void orderParseData(){
        Collections.sort(nameGroup);
        nameList = new ArrayList<>();
        for(String key : nameGroup){
            nameList.add(nameChild.get(key).get(0));
        }
        if(cityGroup.contains(FIRST_CITY)){
            cityGroup.remove(FIRST_CITY);
            Collections.sort(cityGroup);
            Collections.reverse(cityGroup);
            cityGroup.add(FIRST_CITY);
            Collections.reverse(cityGroup);
        }
        List<String> tmpList = (List<String>) bandMap.get("list");
        Map<String, BandObject> tmpMap = (Map<String, BandObject>) bandMap.get("map");
        bandData = new ArrayList<>();
        Collections.sort(tmpList);
        for(String key : tmpList){
            bandData.add(tmpMap.get(key));
        }


    }

    public List<String> getTimeGroup() {
        return timeGroup;
    }

    public Map<String, List<HomeObject>> getTimeChild() {
        return timeChild;
    }

    public List<String> getNameGroup() {
        return nameGroup;
    }

    public Map<String, List<HomeObject>> getNameChild() {
        return nameChild;
    }

    public List<String> getCityGroup() {
        return cityGroup;
    }

    public Map<String, List<HomeObject>> getCityChild() {
        return cityChild;
    }

    public List<BandObject> getBandData() {
        return bandData;
    }

    public List<String> getHistoryTimeGroup() {
        return historyTimeGroup;
    }

    public Map<String, List<HomeObject>> getHistoryTimeChild() {
        return historyTimeChild;
    }

    public List<HomeObject> getNameList() {
        return nameList;
    }
}
