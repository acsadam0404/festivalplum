package hu.festivalplum.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viktor on 2015.03.21..
 */
public class SQLiteUtil extends SQLiteOpenHelper {

    //BASE TABLE
    private static final String BASE_COL_ID = "ID";

    //Favorite TABLE
    private static final String FAV_TAB = "FAVORITE";
    private static final String FAV_COL_PARSE_TAB = "PARSE_TAB";
    private static final String FAV_COL_PARSE_ID = "PARSE_ID";


    //Favorite SQL
    private static final String CREATE_FAV = "CREATE TABLE " + FAV_TAB + " ( " +
            BASE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FAV_COL_PARSE_TAB + " TEXT, " +
            FAV_COL_PARSE_ID + " TEXT )";

    private static final String DATABASE_NAME = "festplum.db";
    private static final int DATABASE_VERSION = 1;

    private static SQLiteUtil instence;

    public SQLiteUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteUtil getInstence(Context context) {
        if(instence == null){
            instence = new SQLiteUtil(context);
        }
        return instence;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + FAV_TAB);
        onCreate(db);
    }

    public List<String> getFavoriteIds(String parseTab){
        String selection = FAV_COL_PARSE_TAB + " LIKE ?";
        List<String> ret = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(FAV_TAB, new String[]{FAV_COL_PARSE_ID}, selection, new String[]{parseTab}, null, null, null);
        while(cursor.moveToNext()){
            ret.add(cursor.getString(0));
        }
        return ret;
    }

    public void addFavoriteId(String parseTab, String parseId){
        if(!isContainFavoriteId(parseTab, parseId)) {
            String insert = "INSERT INTO " + FAV_TAB + " (" + FAV_COL_PARSE_TAB + ", " + FAV_COL_PARSE_ID + ") VALUES ('" + parseTab + "', '" + parseId + "')";
            getWritableDatabase().execSQL(insert);
        }else{
            removeFavoriteId(parseTab, parseId, false);
        }
    }

    public void removeFavoriteId(String parseTab, String parseId, boolean checkContain){
        boolean isDelete = true;
        if(checkContain) {
            isDelete = isContainFavoriteId(parseTab, parseId);
        }
        if(isDelete){
            String delete = "DELETE FROM " + FAV_TAB + " WHERE " + FAV_COL_PARSE_TAB + " LIKE '" + parseTab + "' AND " + FAV_COL_PARSE_ID + " LIKE '" + parseId + "'";
            getWritableDatabase().execSQL(delete);
        }
    }

    private boolean isContainFavoriteId(String parseTab, String parseId){
        boolean ret = false;
        String selection = FAV_COL_PARSE_TAB + " LIKE ? AND " + FAV_COL_PARSE_ID + " LIKE ?";
        Cursor cursor = getReadableDatabase().query(FAV_TAB, new String[]{"count(*)"}, selection, new String[]{parseTab, parseId}, FAV_COL_PARSE_ID, null, null);
        while(cursor.moveToNext()){
            if(cursor.getInt(0) > 0){
                ret = true;
            }
        }
        return ret;
    }
}
