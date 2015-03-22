package hu.festivalplum.home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.festivalplum.home.view.NameView;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentName extends MyFragment {

    private static final String NAME = "Név";

    public FragmentName() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = new NameView(getActivity());
        return v;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
