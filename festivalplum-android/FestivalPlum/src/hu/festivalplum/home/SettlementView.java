package hu.festivalplum.home;

import android.content.Context;
import android.widget.ListView;

/**
 * Created by Ács Ádám on 2015.03.06..
 */
public class SettlementView extends ListView implements HomeViewTab {
    public SettlementView(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Város";
    }
}
