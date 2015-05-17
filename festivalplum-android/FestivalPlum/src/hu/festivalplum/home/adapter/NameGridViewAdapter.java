package hu.festivalplum.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.05.17..
 */
public class NameGridViewAdapter extends NameViewAdapter {
    public NameGridViewAdapter(Context context, List<HomeObject> list, Boolean priorityFilter) {
        super(context, list, priorityFilter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeObject object = (HomeObject)getItem(position);
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView .LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        byte[] img = object.getPlaceImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        imageView.setImageBitmap(Utils.getResizedBitmap(bitmap));

        return imageView;
    }
}
