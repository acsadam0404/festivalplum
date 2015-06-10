package hu.festivalplum.band.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.31..
 */
public class BandViewAdapter extends BaseAdapter {

    private Context context;
    private List<FestivalObject> list;
    private List<String> favoriteIds;

    public BandViewAdapter(Context context, List<FestivalObject> list){
        this.context = context;
        this.list = list;
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
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
            convertView = infalInflater.inflate(R.layout.festivalview_item, null);
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

        name.setText(child.getPlaceName() + " - " + child.getStageName());
        date.setText(Utils.getSdf(context, Utils.sdfDate).format(child.getStartDate()) + " (" +Utils.getSdf(context, Utils.sdfTime).format(child.getStartDate()) + " - " + Utils.getSdf(context, Utils.sdfTime).format(child.getToDate()) + ")");

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        List<String> concertIds = SQLiteUtil.getInstence(context).getFavoriteIds("Concert");
        this.list = ParseDataCollector.collectFavoriteData(concertIds, Utils.getLanguageCode(context));
        super.notifyDataSetChanged();
    }
}
