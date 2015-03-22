package hu.festivalplum.home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.festivalplum.home.view.ArtistView;

/**
 * Created by viktor on 2015.03.15..
 */
public class FragmentBand extends MyFragment {

    private static final String NAME = "Fellépő";

    public FragmentBand() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = new ArtistView(getActivity());
        return v;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
