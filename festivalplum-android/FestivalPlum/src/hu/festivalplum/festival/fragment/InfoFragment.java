package hu.festivalplum.festival.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;

import hu.festivalplum.R;
import hu.festivalplum.festival.FestivalActivity;
import hu.festivalplum.festival.fragment.MyFragment;

/**
 * Created by viktor on 2015.03.26..
 */
public class InfoFragment extends MyFragment {

    public InfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*
        String info = "<html><body><p>Teszt Info</p></body></html>"; //((FestivalActivity)getActivity()).getFestivalInfo();
        WebView view = new WebView(getActivity());
        view.getSettings().setJavaScriptEnabled(false);
        view.loadData(info, "text/html", "UTF-8");
        return view;
        */
        return new View(getActivity());
    }
    @Override
    public int getName() {
        return R.string.tab_info;
    }
}
