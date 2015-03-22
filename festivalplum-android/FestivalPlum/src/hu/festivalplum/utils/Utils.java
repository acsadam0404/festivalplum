package hu.festivalplum.utils;

import android.widget.ImageView;

/**
 * Created by viktor on 2015.03.18..
 */
public class Utils {
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
