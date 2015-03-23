package hu.festivalplum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import hu.festivalplum.home.HomeActivity;


public class StarterActivity extends Activity {

    private final static long SHOW_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        getActionBar().hide();
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
