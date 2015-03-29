package hu.festivalplum.utils;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

/**
 * Created by viktor on 2015.03.18..
 */
public class Utils {

    public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy.MM.dd");
    public static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH:mm");

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
}
