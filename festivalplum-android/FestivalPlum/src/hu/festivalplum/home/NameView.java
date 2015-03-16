package hu.festivalplum.home;

import android.content.Context;
import android.widget.ListView;

/**
 * Created by Ács Ádám on 2015.03.06..
 */
public class NameView extends ListView implements HomeViewTab {
    public NameView(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Név";
    }
}
