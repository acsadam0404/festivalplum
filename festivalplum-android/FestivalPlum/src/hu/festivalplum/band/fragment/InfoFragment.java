package hu.festivalplum.band.fragment;

import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import hu.festivalplum.R;
import hu.festivalplum.band.BandActivity;

/**
 * Created by viktor on 2015.03.31..
 */
public class InfoFragment extends MyFragment {

    public InfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String info = ((BandActivity)getActivity()).getInfo();
        WebView view = new WebView(getActivity());

        String base64 = "";
        try {
            base64 = android.util.Base64.encodeToString(info.getBytes("UTF-8"), android.util.Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        view.loadData(base64, "text/html; charset=utf-8", "base64");

        return view;
    }
    @Override
    public int getName() {
        return R.string.tab_info;
    }
}
