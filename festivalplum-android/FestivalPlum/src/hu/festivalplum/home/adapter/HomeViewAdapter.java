package hu.festivalplum.home.adapter;

import java.util.ArrayList;
import java.util.HashMap;
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
import hu.festivalplum.R;
import hu.festivalplum.model.HomeObject;
import hu.festivalplum.utils.Utils;

public class HomeViewAdapter extends BaseExpandableListAdapter {

    protected Context context;

    private List<String> baseHeaderTitles;
    private Map<String, List<HomeObject>> baseChildTitles;
    private List<String> headerTitles;
    private Map<String, List<HomeObject>> childTitles;

    public HomeViewAdapter(Context context, List<String> headerTitles, Map<String, List<HomeObject>> childTitles) {
        this.context = context;
        this.headerTitles = headerTitles;
        this.childTitles = childTitles;
        this.baseHeaderTitles = headerTitles;
        this.baseChildTitles = childTitles;
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

        name.setText(child.getPlaceName() + " - " + child.getCityName());
        date.setText(Utils.sdfDate.format(child.getStartDate()));
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

    public void filter(String query){
        this.headerTitles = new ArrayList<>();
        this.childTitles = new HashMap<>();
        query = query.toLowerCase();

        for(String key : baseHeaderTitles){
            List<HomeObject> tmpChildList = baseChildTitles.get(key);
            for(HomeObject o : tmpChildList){
                if(o.getPlaceName().toLowerCase().contains(query)){
                    if(childTitles.containsKey(key)){
                        childTitles.get(key).add(o);
                    }else{
                        List<HomeObject> fTmpChildList = new ArrayList<>();
                        fTmpChildList.add(o);
                        childTitles.put(key, fTmpChildList);
                        headerTitles.add(key);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}