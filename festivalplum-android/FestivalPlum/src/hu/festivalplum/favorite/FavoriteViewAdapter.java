package hu.festivalplum.favorite;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hu.festivalplum.MapActivity;
import hu.festivalplum.R;
import hu.festivalplum.home.HomeObject;

/**
 * Created by viktor on 2015.03.19..
 */
public class FavoriteViewAdapter extends BaseAdapter {

    private Context context;
    private List<HomeObject> list;

    public FavoriteViewAdapter(Context context, List<HomeObject> list){
        this.context = context;
        this.list = list;
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
        HomeObject item = (HomeObject)getItem(i);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.favoriteview_item, null);
        }

        TextView txtListChild = (TextView) view.findViewById(R.id.name);



        ImageView like = (ImageView) view.findViewById(R.id.map);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MapActivity.class);
                context.startActivity(i);
            }
        });

        txtListChild.setText(item.getPlaceName() + " / " + item.getCityName());

        return view;
    }
}
