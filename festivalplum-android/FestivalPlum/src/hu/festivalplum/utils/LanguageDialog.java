package hu.festivalplum.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.HashMap;
import java.util.Map;

import hu.festivalplum.R;

/**
 * Created by viktor on 2015.05.16..
 */
public class LanguageDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    Map<String,Integer> langMap;
    String selected;

    public LanguageDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.language_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        RadioButton radioHu = (RadioButton) findViewById(R.id.radioHu);
        RadioButton radioEn = (RadioButton) findViewById(R.id.radioEn);
        radioHu.setOnClickListener(this);
        radioEn.setOnClickListener(this);

        initLang();

    }

    public void initLang(){
        langMap = new HashMap<>();
        langMap.put("hu", R.id.radioHu);
        langMap.put("en", R.id.radioEn);

        selected = Utils.getLanguageCode(c);

        RadioButton radio;
        if(langMap.containsKey(selected)){
            radio = (RadioButton) findViewById(langMap.get(selected));
            radio.setChecked(true);
        }
    }

    private void radioClick(){
        RadioButton radio;
        String lc = selected;
        for (Object key : langMap.keySet()) {
            radio = (RadioButton) findViewById(langMap.get(key.toString()));
            if(!lc.equals(key.toString()))
                radio.setChecked(false);
            else{
                radio.setChecked(true);
                selected = key.toString();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                Utils.setLanguageCode(c, selected);

                break;
            case R.id.btn_no:
                dismiss();
                break;
            case R.id.radioEn:
                selected = "en";
                radioClick();
                break;
            case R.id.radioHu:
                selected = "hu";
                radioClick();
                break;
            default:
                break;
        }

    }

}
