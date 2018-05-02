package vinhnb.gvn.com.playmedia.util;

import android.content.Context;

public class MediaPlayAppUtils extends android.app.Application {
    /*
     * var
     * */
    private static MediaPlayAppUtils sInstance;

    /*
     * contructor
     * */
    public MediaPlayAppUtils() {
        sInstance = this;
    }

    /*
     * func
     * */
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

}