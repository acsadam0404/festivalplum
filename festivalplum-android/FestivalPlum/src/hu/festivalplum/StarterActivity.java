package hu.festivalplum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;

import hu.festivalplum.home.HomeActivity;


public class StarterActivity extends Activity {

    private final static long SHOW_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FPApplication.getInstence().initParseData(this);
        LayoutInflater infalInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = infalInflater.inflate(R.layout.activity_starter, null);

        setContentView(contentView);
        getActionBar().hide();

        AlphaAnimation animation = new AlphaAnimation(1.0f, 0f);
        animation.setDuration(3000);
        animation.setStartOffset(0);
        animation.setFillAfter(true);
        contentView.startAnimation(animation);

        Thread t = new Thread(){
            public void run(){
                try{
                    sleep(SHOW_TIME);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(StarterActivity.this, HomeActivity.class);
                    startActivity(i);

                    finish();
                }
            }
        };
        t.start();
    }
}
