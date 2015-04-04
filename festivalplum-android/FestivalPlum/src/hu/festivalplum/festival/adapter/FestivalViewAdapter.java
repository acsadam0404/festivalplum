package hu.festivalplum.festival.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.home.HomeActivity;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.18..
 */
public class FestivalViewAdapter  extends BaseExpandableListAdapter {

    private Context context;

    private List<String> baseHeaderTitles;
    private Map<String, List<FestivalObject>> baseChildTitles;
    private List<String> headerTitles;
    private Map<String, List<FestivalObject>> childTitles;

    private List<String> favoriteIds;

    private  Map<String, List<FestivalObject>> firstGroupChildInStage;

    public FestivalViewAdapter(Context context, List<String> headerTitles, Map<String, List<FestivalObject>> childTitles) {
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
        this.context = context;
        this.headerTitles = headerTitles;
        this.childTitles = childTitles;
        this.baseHeaderTitles = headerTitles;
        this.baseChildTitles = childTitles;
        setChildInStage();
        liveTime();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childTitles.get(this.headerTitles.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
        FestivalObject child = (FestivalObject) getChild(groupPosition, childPosition);
        /*
        if(childPosition == 0 && groupPosition == 0) {
            String childDate = Utils.getSdf(context, Utils.sdfDate).format(child.getStartDate());
            String date = Utils.getSdf(context, Utils.sdfDate).format(new Date());
            if (childDate.equals(date)) {
                liveTime();
            }
        }
        */
        if(favoriteIds.contains(child.getConcertId())){
            child.setFavorite(true);
        }else{
            child.setFavorite(false);
        }

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.festivalview_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        byte[] img = child.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        image.setImageBitmap(bitmap);

        ImageView like = (ImageView)convertView.findViewById(R.id.like);
        Utils.setFavoriteImage(like,child.isFavorite());
        like.setTag(child);

        name.setText(child.getBandName() + " - " + child.getStageName());
        date.setText(Utils.getSdf(context, Utils.sdfTime).format(child.getStartDate()) + " - " + Utils.getSdf(context, Utils.sdfTime).format(child.getToDate()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childTitles.get(this.headerTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerTitles.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headerTitles.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.homeview_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
        super.notifyDataSetChanged();
    }

    public void filter(String query){
        this.headerTitles = new ArrayList<>();
        this.childTitles = new HashMap<>();
        query = query.toLowerCase();

        for(String key : baseHeaderTitles){
            List<FestivalObject> tmpChildList = baseChildTitles.get(key);
            for(FestivalObject o : tmpChildList){
                if(o.getBandName().toLowerCase().contains(query)){
                    if(childTitles.containsKey(key)){
                        childTitles.get(key).add(o);
                    }else{
                        List<FestivalObject> fTmpChildList = new ArrayList<>();
                        fTmpChildList.add(o);
                        childTitles.put(key, fTmpChildList);
                        headerTitles.add(key);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    private void liveTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Új
                if(getGroupCount() > 0 && getChildrenCount(0) > 0){
                    for (Object key : firstGroupChildInStage.keySet()) {
                        if(firstGroupChildInStage.get(key.toString()).size() > 1){
                            checkNextStart(firstGroupChildInStage.get(key.toString()).get(0),firstGroupChildInStage.get(key.toString()).get(1));
                        }else if(firstGroupChildInStage.get(key.toString()).size() > 0){
                            checkTwoHour(firstGroupChildInStage.get(key.toString()).get(0));
                        }
                    }
                } else if(getGroupCount() > 0){
                    removeFirstGroup();
                }
                // Régi
                /*
                if(getGroupCount() > 0 && getChildrenCount(0) > 0){
                    FestivalObject child = (FestivalObject)FestivalViewAdapter.this.getChild(0, 0);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.HOUR_OF_DAY, -2);
                    Calendar childCal = Calendar.getInstance();
                    childCal.setTime(child.getStartDate());
                    if(cal.after(childCal)){
                        removeFirstChild();
                    }

                } else if(getGroupCount() > 0){
                    removeFirstGroup();
                }
                */
                liveTime();
            }
        }, 10000);
    }

    private void checkTwoHour(FestivalObject child){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -2);
        Calendar childCal = Calendar.getInstance();
        childCal.setTime(child.getStartDate());
        if(cal.after(childCal)){
            removeChild(child);
        }
    }

    private void checkNextStart(FestivalObject child, FestivalObject nextChild){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Calendar nextCal = Calendar.getInstance();
        nextCal.setTime(nextChild.getStartDate());
        if(cal.after(nextCal)){
            removeChild(child);
        }else{
            checkTwoHour(child);
        }
    }

    private void removeChild(FestivalObject child){
        String key = (String) getGroup(0);

        this.baseChildTitles.get(key).remove(child);
        this.childTitles.get(key).remove(child);

        setChildInStage();
        notifyDataSetChanged();
    }

    private void removeFirstChild(){
        String key = (String) getGroup(0);
        FestivalObject firstChild = (FestivalObject) getChild(0, 0);
        this.baseChildTitles.get(key).remove(firstChild);
        this.childTitles.get(key).remove(firstChild);
        notifyDataSetChanged();
    }

    private void removeFirstGroup(){
        String key = (String) getGroup(0);
        this.baseChildTitles.remove(key);
        this.childTitles.remove(key);
        this.baseHeaderTitles.remove(key);
        this.headerTitles.remove(key);
        setChildInStage();
        notifyDataSetChanged();
    }

    private void setChildInStage(){
        if(getGroupCount() > 0){
            firstGroupChildInStage = new HashMap<>();
            String key = (String) getGroup(0);
            List<FestivalObject> firstGroupChildList = this.baseChildTitles.get(key);
            for(FestivalObject o : firstGroupChildList){
                if(firstGroupChildInStage.containsKey(o.getStageName())){
                    firstGroupChildInStage.get(o.getStageName()).add(o);
                }else{
                    List<FestivalObject> tmpList = new ArrayList<>();
                    tmpList.add(o);
                    firstGroupChildInStage.put(o.getStageName(),tmpList);
                }
            }
        }
    }

}