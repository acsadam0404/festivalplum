package hu.festivalplum.home.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.festivalplum.R;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

public class HomeViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    /** header titles */
    private List<String> headerTitles;
    /** child data in format of header title, child title */
    private Map<String, List<HomeObject>> childTitles;

    private List<String> favoriteIds;

    public HomeViewAdapter(Context context, List<String> headerTitles, Map<String, List<HomeObject>> childTitles) {
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Event");
        this.context = context;
        this.headerTitles = headerTitles;
        this.childTitles = childTitles;

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
        HomeObject child = (HomeObject) getChild(groupPosition, childPosition);
        if(favoriteIds.contains(child.getEventId())){
            child.setFavorite(true);
        }

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.homeview_child, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.name);

        byte[] img = child.getPlaceImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        image.setImageBitmap(bitmap);

        ImageView like = (ImageView)convertView.findViewById(R.id.like);
        Utils.setFavoriteImage(like,child.isFavorite());
        like.setTag(child);

        txtListChild.setText(child.getPlaceName() + " / " + child.getCityName());
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
        this.favoriteIds = SQLiteUtil.getInstence(context).getFavoriteIds("Event");
        super.notifyDataSetChanged();
    }
}