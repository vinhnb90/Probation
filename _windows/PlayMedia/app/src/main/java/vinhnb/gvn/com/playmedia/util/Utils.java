package vinhnb.gvn.com.playmedia.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import vinhnb.gvn.com.playmedia.R;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class Utils {
    /*
     * var
     * */

    public static final int TIME_DELAY = 2000;
    public static final int REQUEST_CODE_PERMISSION = 1;
    public static final int REQUEST_CODE_DETAIL_MEDIA = 2;
    public static final String INTENT_CODE_DETAIL_MEDIA_POSITION_PLAY_NOW = "INTENT_CODE_DETAIL_MEDIA_POSITION_PLAY_NOW";
    public static final String INTENT_CODE_DETAIL_MEDIA_LIST = "INTENT_CODE_DETAIL_MEDIA_LIST";

    public static final int WIDTH_MEDIA_DEFAULT = 200;
    public static final int HEIGHT_MEDIA_DEFAULT = 150;
    public static final String ARG_POSITION_MEDIA = "ARG_POSITION_MEDIA";

    public static void runAnimationView(Activity activity, @NonNull final View view, int idAnim, @IntRange(from = 0) int timeDelayAmim) throws Exception {
        final Animation animation = AnimationUtils.loadAnimation(view.getContext(), idAnim);
        if (timeDelayAmim > 0)
            animation.setDuration(idAnim);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.startAnimation(animation);
            }
        });
    }

    public static boolean checkPermissionApp(Activity activity, String[] permissionList) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false;

        boolean isOK = false;
        Context context = activity.getApplicationContext();
        for (String permission :
                permissionList) {
            isOK = isOK && (checkSelfPermission(context, permission) != PERMISSION_GRANTED);
        }

        if (!isOK) {
            requestPermissions(activity, permissionList, REQUEST_CODE_PERMISSION);
            return false;
        }
        return true;
    }

    public static boolean checkStatusPermission(int[] grantResults, String[] permissionList) {
        //check result grant permission
        if (grantResults.length == 0)
            return false;

        boolean isOK = true;
        for (int indexGrantResult :
                grantResults) {
            if (indexGrantResult != PERMISSION_GRANTED) {
                isOK = false;
                break;
            }
        }

        return isOK;
    }

    public static String getTagLog(Class<?> classz) {
        if (classz == null)
            return "Classs zzzz!";

        return classz.getSimpleName();
    }


    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

}
