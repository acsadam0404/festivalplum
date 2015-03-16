package hu.festivalplum;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class FPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "EHBh2sz9yo6a99A5UMDjYBKPGWD9zpl35JhSZQG8", "lUXrxJ1xNJ7tQKVeE7dXt7kTP3bjECh8Wuestyjv");
        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().saveInBackground();
        ParseACL defaultACL = new ParseACL();
        //defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
