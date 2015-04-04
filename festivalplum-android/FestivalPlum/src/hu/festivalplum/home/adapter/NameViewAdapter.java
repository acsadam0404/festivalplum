package hu.festivalplum.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.29..
 */
public class NameViewAdapter  extends BaseAdapter implements SectionIndexer {

    private Context context;
    private List<HomeObject> list;
    private List<HomeObject> baseList;

    public NameViewAdapter(Context context, List<HomeObject> list){
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
        HomeObject object = (HomeObject)getItem(i);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.homeview_child, null);
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView date = (TextView) view.findViewById(R.id.date);

        byte[] img = object.getPlaceImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageBitmap(bitmap);

        name.setText(object.getPlaceName() + " - " + object.getCityName());
        date.setText(Utils.getSdf(context, Utils.sdfDate).format(object.getStartDate()));

        return view;
    }

    private void setSection(LinearLayout header, String label) {
        TextView text = new TextView(context);
        header.setBackgroundColor(0xffaabbcc);
        text.setTextColor(Color.WHITE);
        text.setText(label.substring(0, 1).toUpperCase());
        text.setTextSize(20);
        text.setPadding(5, 0, 0, 0);
        text.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text);
    }
    public int getPositionForSection(int section) {
        if (section == 35) {
            return 0;
        }
        for (int i = 0; i < list.size(); i++) {
            String l = list.get(i).getPlaceName();
            char firstChar = l.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
    public int getSectionForPosition(int arg0) {
        return 0;
    }
    public Object[] getSections() {
        return null;
    }

    public void filter(String query){
        this.list = new ArrayList<>();
        query = query.toLowerCase();

        for(HomeObject o : baseList){
            if(o.getPlaceName().toLowerCase().contains(query)){
                this.list.add(o);
            }
        }

        notifyDataSetChanged();
    }
}
