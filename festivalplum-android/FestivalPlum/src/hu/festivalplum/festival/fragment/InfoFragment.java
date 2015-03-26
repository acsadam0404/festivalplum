package hu.festivalplum.festival.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import hu.festivalplum.festival.fragment.MyFragment;

/**
 * Created by viktor on 2015.03.26..
 */
public class InfoFragment extends MyFragment {

    private static final String NAME = "Info";

    public InfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = new ListView(getActivity());
        return v;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
