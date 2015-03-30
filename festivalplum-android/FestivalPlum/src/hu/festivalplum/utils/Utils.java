package hu.festivalplum.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import hu.festivalplum.FPApplication;
import hu.festivalplum.StarterActivity;

/**
 * Created by viktor on 2015.03.18..
 */
public class Utils {

    public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy.MM.dd");
    public static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH:mm");

    private static final String LANGUAGE_CODE = "hu.festivalplum.language";

    public static final String[] month = new String[]{"Január","Február","Március","Április","Május","Június","Július","Augusztus","Szeptember","Október","November","December"};

    public static String getMonthName(int m){
        if(m >= 0 && m < 12){
            return month[m];
        }
        return "";
    }

    public static void setFavoriteImage(ImageView v, boolean favorite){
        if(favorite){
            ((ImageView)v).setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            ((ImageView)v).setImageResource(android.R.drawable.btn_star_big_off);
        }
    }


    public static void setLanguage(Context context){
        String languageCode = Utils.getLanguageCode(context);
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(languageCode.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    private static String getLanguageCode(Context context){
        SharedPreferences prefs = context.getSharedPreferences("hu.festivalplum", Context.MODE_PRIVATE);
        return prefs.getString(LANGUAGE_CODE, "en");
    }

    public static void setLanguageCode(Context context, String languageCode){
        SharedPreferences prefs = context.getSharedPreferences("hu.festivalplum", Context.MODE_PRIVATE);
        prefs.edit().putString(LANGUAGE_CODE, languageCode).apply();
        Utils.setLanguage(context);
        Intent i = new Intent(context, StarterActivity.class);
        context.startActivity(i);
        FPApplication.initParseData();
    }

}
