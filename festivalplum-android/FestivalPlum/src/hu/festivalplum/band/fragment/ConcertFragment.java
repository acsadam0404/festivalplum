package hu.festivalplum.band.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import hu.festivalplum.R;
import hu.festivalplum.band.BandActivity;
import hu.festivalplum.band.adapter.BandViewAdapter;
import hu.festivalplum.model.FestivalObject;

/**
 * Created by viktor on 2015.03.31..
 */
public class ConcertFragment extends MyFragment {

    private List<FestivalObject> concertList;

    public ConcertFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();

        ListView view = new ListView(context);

        concertList = ((BandActivity)context).getConcertList();

        BandViewAdapter bandViewAdapter = new BandViewAdapter(context, concertList);
        view.setAdapter(bandViewAdapter);

        return view;
    }

    @Override
    public int getName() {
        return R.string.tab_program;
    }
}
