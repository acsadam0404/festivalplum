package hu.festivalplum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by viktor on 2015.03.31..
 */
public class BandInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        String band  = intent.getStringExtra("band");
        getActionBar().setTitle(band);
        String base64 = "";
        try {
            base64 = android.util.Base64.encodeToString(info.getBytes("UTF-8"), android.util.Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        WebView view = (WebView) findViewById(R.id.webView1);
        view.loadData(base64, "text/html; charset=utf-8", "base64");

    }
}
