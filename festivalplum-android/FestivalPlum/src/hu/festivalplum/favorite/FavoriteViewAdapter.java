package hu.festivalplum.favorite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.19..
 */
public class FavoriteViewAdapter extends BaseAdapter {

    private Context context;
    private List<FestivalObject> list;
    private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

    private List<String> favoriteIds;

    public FavoriteViewAdapter(Context context, List<FestivalObject> list){
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
        this.context = context;
        this.list = list;
        //liveTime();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        FestivalObject child = (FestivalObject) getItem(i);
        if(favoriteIds.contains(child.getConcertId())){
            child.setFavorite(true);
        }else{
            child.setFavorite(false);
        }

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.favoriteview_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        byte[] img = child.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        image.setImageBitmap(Utils.getResizedBitmap(bitmap));

        ImageView like = (ImageView)convertView.findViewById(R.id.like);
        Utils.setFavoriteImage(like,child.isFavorite());
        like.setTag(child);

        ImageView active = (ImageView)convertView.findViewById(R.id.active);
        if(isOnline(child)){
            active.setImageResource(android.R.drawable.presence_online);
        }else{
            active.setImageResource(0);
        }

        name.setText(child.getBandName() + "\n" + child.getPlaceName() + " - " + child.getStageName());
        date.setText(Utils.getSdf(context, Utils.sdfMMdd).format(child.getStartDate()) + " " + Utils.getSdf(context, Utils.sdfTime).format(child.getStartDate()));

        if (mSelection.get(i) != null) {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_purple));
        }else{
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.background_light));
        }


        return convertView;
    }

    public void setNewSelection(int position, boolean value) {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }

    public List<FestivalObject> getAllSelectedItem(){
        List<FestivalObject> ret = new ArrayList<>();
        for(Integer i : mSelection.keySet()){
            ret.add((FestivalObject)getItem(i));
        }
        return ret;
    }

    @Override
    public void notifyDataSetChanged() {
        List<String> concertIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
        this.list = ParseDataCollector.collectFavoriteData(concertIds, Utils.getLanguageCode(context));
        super.notifyDataSetChanged();
    }

    private void liveTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                notifyDataSetChanged();
                liveTime();
            }
        }, 10000);
    }

    private Boolean isOnline(FestivalObject child){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, +2);
        Calendar childCal = Calendar.getInstance();
        childCal.setTime(child.getStartDate());
        if(cal.after(childCal) && childCal.before(cal2)){
           return true;
        }
        return false;
    }

}
