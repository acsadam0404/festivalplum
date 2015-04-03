package hu.festivalplum.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import hu.festivalplum.R;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.04.03..
 */
public class CityViewAdapter extends HomeViewAdapter {

    public CityViewAdapter(Context context, List<String> headerTitles, Map<String, List<HomeObject>> childTitles) {
        super(context,headerTitles,childTitles);
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
        HomeObject child = (HomeObject) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.homeview_child, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        byte[] img = child.getPlaceImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        image.setImageBitmap(bitmap);

        name.setText(child.getPlaceName());
        date.setText(Utils.sdfDate.format(child.getStartDate()) + " - " + Utils.sdfDate.format(child.getEndDate()));
        return convertView;
    }

}
