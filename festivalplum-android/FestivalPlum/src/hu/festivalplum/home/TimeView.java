package hu.festivalplum.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.festivalplum.FestivalActivity;

/**
 * Created by Ács Ádám on 2015.02.20..
 */
public class TimeView extends ExpandableListView implements HomeViewTab {
    private List<String> headerTitles;
    private Map<String, List<String>> childTitles;

    public TimeView(final Context context) {
        super(context);

        prepareListData();

        setAdapter(new TimeViewAdapter(context, headerTitles, childTitles));

        setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FestivalActivity.class);
                context.startActivity(intent);
                return true;
            }
        });
    }

    private void prepareListData() {
        headerTitles = new ArrayList<String>();
        childTitles = new HashMap<String, List<String>>();

        // Adding child data
        headerTitles.add("Top 250");
        headerTitles.add("Now Showing");
        headerTitles.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        childTitles.put(headerTitles.get(0), top250); // Header, Child data
        childTitles.put(headerTitles.get(1), nowShowing);
        childTitles.put(headerTitles.get(2), comingSoon);
    }

    @Override
    public String getName() {
        return "Idő";
    }

    /*
    private static class Adapter extends ParseQueryAdapter {
        public Adapter(Context context) {
            super(context, "Place");

        }

        @Override
        public View getItemView(ParseObject object, View v, ViewGroup parent) {
            if (v == null) {
                v = View.inflate(getContext(), R.layout.timeview_item, null);
            }
            final View view = v;
            super.getItemView(object, v, parent);

            TextView nameView = (TextView) v.findViewById(R.id.name);
            nameView.setText(object.getString("name"));

            ParseFile fileObject = (ParseFile) object.get("image");
            fileObject.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.image);
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imageView.setImageBitmap(bmp);
                }
            });

            return v;
        }
    }
        */
}
