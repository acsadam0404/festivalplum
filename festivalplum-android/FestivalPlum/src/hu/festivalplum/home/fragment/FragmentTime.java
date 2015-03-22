package hu.festivalplum.home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.festivalplum.home.view.TimeView;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentTime extends MyFragment {

    private static final String NAME = "Idő";

    public FragmentTime() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = new TimeView(getActivity());
        return v;
    }

    @Override
    public String getName() {
        return NAME;
    }

}

