package hu.festivalplum.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import hu.festivalplum.FPApplication;
import hu.festivalplum.R;
import hu.festivalplum.StarterActivity;

/**
 * Created by viktor on 2015.03.18..
 */
public class Utils {

    public static final int sdfDate = R.string.sdfDate;
    public static final int sdfMMdd = R.string.sdfMMdd;
    public static final int sdfTime = R.string.sdfTime;
    public static final int sdf = R.string.sdf;

    private static final String LANGUAGE_CODE = "hu.festivalplum.language";

    private static final int months = R.array.months;

    public static String getMonthName(Context context, int m){
        String [] monthArray = context.getResources().getStringArray(months);
        if(m >= 0 && m < 12){
            return monthArray[m];
        }
        return "";
    }

    public static SimpleDateFormat getSdf(Context context, int key){
        return new SimpleDateFormat(context.getResources().getString(key));
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

    public static String getLanguageCode(Context context){
        SharedPreferences prefs = context.getSharedPreferences("hu.festivalplum", Context.MODE_PRIVATE);
        return prefs.getString(LANGUAGE_CODE, "en");
    }

    public static void setLanguageCode(Context context, String languageCode){
        SharedPreferences prefs = context.getSharedPreferences("hu.festivalplum", Context.MODE_PRIVATE);
        prefs.edit().putString(LANGUAGE_CODE, languageCode).apply();
        Utils.setLanguage(context);
        Intent i = new Intent(context, StarterActivity.class);
        context.startActivity(i);
        FPApplication.initParseData(context);
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap) {
        return getResizedBitmap(bitmap, 100, 100);
    }

}
