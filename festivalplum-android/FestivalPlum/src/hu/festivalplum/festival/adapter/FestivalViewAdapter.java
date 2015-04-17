package hu.festivalplum.festival.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.04.17..
 */
public class FestivalViewAdapter extends BaseAdapter {

    private Context context;
    private List<FestivalObject> list;
    private List<FestivalObject> baseList;
    private List<String> favoriteIds;

    public FestivalViewAdapter(Context context, List<FestivalObject> list){
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
        this.context = context;
        this.list = list;
        this.baseList = list;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        FestivalObject child = (FestivalObject) getItem(i);

        if(favoriteIds.contains(child.getConcertId())){
            child.setFavorite(true);
        }else{
            child.setFavorite(false);
        }

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.festivalview_item, null);
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView date = (TextView) view.findViewById(R.id.date);

        byte[] img = child.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageBitmap(Utils.getResizedBitmap(bitmap));

        ImageView like = (ImageView)view.findViewById(R.id.like);
        Utils.setFavoriteImage(like,child.isFavorite());
        like.setTag(child);

        name.setText(child.getBandName());
        date.setText(child.getStageName() + " - " + Utils.getSdf(context, Utils.sdfTime).format(child.getStartDate()));
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
        super.notifyDataSetChanged();
    }

    public void filter(String query){
        this.list = new ArrayList<>();
        query = query.toLowerCase();

        for(FestivalObject o : baseList){
            if(o.getBandName().toLowerCase().contains(query)){
                this.list.add(o);
            }
        }
        notifyDataSetChanged();
    }
}
