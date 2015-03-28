package hu.festivalplum.home.adapter;

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
import hu.festivalplum.model.BandObject;

/**
 * Created by viktor on 2015.03.28..
 */
public class BandViewAdapter extends BaseAdapter {

    private Context context;
    private List<BandObject> list;

    public BandViewAdapter(Context context, List<BandObject> list){
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
        BandObject band = (BandObject)getItem(i);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.bandview_item, null);
        }

        byte[] img = band.getBandImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageBitmap(bitmap);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView style = (TextView) view.findViewById(R.id.style);

        name.setText(band.getName());
        style.setText(band.getStyle());
        return view;
    }
}
