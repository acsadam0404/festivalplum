package hu.festivalplum.band;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.band.adapter.FragmentAdapter;
import hu.festivalplum.model.FestivalObject;
import hu.festivalplum.utils.ParseDataCollector;
import hu.festivalplum.utils.SQLiteUtil;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.03.31..
 */
public class BandActivity extends FragmentActivity {


    private String info;

    private FragmentAdapter fragmentAdapter;
    private PagerSlidingTabStrip tabs;

    private List<FestivalObject> concertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String bandId = intent.getStringExtra("bandId");
        info = intent.getStringExtra("info");
        String name = intent.getStringExtra("name");
        getActionBar().setTitle(name);
        concertList = ParseDataCollector.collectBandConcerts(this, bandId);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);

        ViewPager p = (ViewPager) findViewById(R.id.pager);
        p.setAdapter(fragmentAdapter);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(p);

        LinearLayout layout =  (LinearLayout)tabs.getChildAt(0);
        TextView view = (TextView)layout.getChildAt(0);
        view.setTextColor(getResources().getColor(android.R.color.holo_purple));

        tabs.delegatePageListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                LinearLayout layout =  (LinearLayout)tabs.getChildAt(0);
                int tabNum = layout.getChildCount();

                for (int i = 0; i < tabNum; i++) {
                    TextView view = (TextView)layout.getChildAt(i);
                    view.setTextColor(0xFF666666);
                }
                TextView view = (TextView)layout.getChildAt(position);
                view.setTextColor(BandActivity.this.getResources().getColor(android.R.color.holo_purple));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        };


    }

    public void likeHandler (final View v) {
        final FestivalObject item = (FestivalObject)v.getTag();
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_delete_favorite_title)
                .setMessage(R.string.dialog_delete_favorite_msg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteUtil.getInstence(BandActivity.this).addFavoriteId("Concert", item.getConcertId());
                        item.setFavorite(!item.isFavorite());
                        Utils.setFavoriteImage((ImageView) v, item.isFavorite());
                        fragmentAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    public String getInfo() {
        return info;
    }

    public List<FestivalObject> getConcertList() {
        return concertList;
    }
}
