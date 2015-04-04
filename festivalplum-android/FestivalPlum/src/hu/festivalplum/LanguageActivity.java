package hu.festivalplum;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import java.util.HashMap;
import java.util.Map;
import hu.festivalplum.utils.Utils;

/**
 * Created by viktor on 2015.04.04..
 */
public class LanguageActivity extends Activity {

    private View contentView;

    Map<String,Integer> langMap;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater infalInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = infalInflater.inflate(R.layout.activity_language, null);

        setContentView(contentView);
        getActionBar().hide();

        initLang();
    }

    public void initLang(){
        langMap = new HashMap<>();
        langMap.put("hu", R.id.radioHu);
        langMap.put("en", R.id.radioEn);

        selected = Utils.getLanguageCode(this);

        RadioButton radio;
        if(langMap.containsKey(selected)){
            radio = (RadioButton) contentView.findViewById(langMap.get(selected));
            radio.setChecked(true);
        }
    }

    public void radioClick(final View v){
        RadioButton radio;
        for (Object key : langMap.keySet()) {
            radio = (RadioButton) contentView.findViewById(langMap.get(key.toString()));
            if(!radio.equals(v))
                radio.setChecked(false);
            else{
                radio.setChecked(true);
                selected = key.toString();
            }
        }
    }

    public void okClick (final View v) {
        Utils.setLanguageCode(this, selected);
        this.finish();
    }
}
