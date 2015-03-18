package hu.festivalplum.home;

/**
 * Created by viktor on 2015.03.18..
 */
public class HomeUtil {
    public static final String[] month = new String[]{"Január","Február","Március","Április","Május","Június","Július","Augusztus","Szeptember","Október","November","December"};

    public static String getMonthName(int m){
        if(m >= 0 && m < 12){
            return month[m];
        }
        return "";
    }
}
