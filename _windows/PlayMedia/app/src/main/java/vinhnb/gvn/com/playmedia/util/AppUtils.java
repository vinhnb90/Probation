package vinhnb.gvn.com.playmedia.util;

import android.content.Context;

public class AppUtils extends android.app.Application {
    /*
     * var
     * */
    private static AppUtils sInstance;

    /*
     * contructor
     * */
    public AppUtils() {
        sInstance = this;
    }

    /*
     * func
     * */
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

}