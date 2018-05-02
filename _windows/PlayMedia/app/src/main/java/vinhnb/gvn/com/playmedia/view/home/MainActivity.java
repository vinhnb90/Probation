package vinhnb.gvn.com.playmedia.view.home;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import vinhnb.gvn.com.playmedia.R;
import vinhnb.gvn.com.playmedia.util.Utils;
import vinhnb.gvn.com.playmedia.view.base.BaseActivity;
import vinhnb.gvn.com.playmedia.view.listMedia.ListMediaActivity;

import static vinhnb.gvn.com.playmedia.util.Utils.REQUEST_CODE_PERMISSION;
import static vinhnb.gvn.com.playmedia.util.Utils.TIME_DELAY;
import static vinhnb.gvn.com.playmedia.util.Utils.checkPermissionApp;
import static vinhnb.gvn.com.playmedia.util.Utils.checkStatusPermission;
import static vinhnb.gvn.com.playmedia.util.Utils.getTagLog;
import static vinhnb.gvn.com.playmedia.util.Utils.runAnimationView;


public class MainActivity extends BaseActivity {

    /*
     * variable
     * */
    public static final String[] PERMISSION_LIST = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    /*
     * lifecycle
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        full screen
        super.setFullScreen();
        setContentView(R.layout.activity_main);

//        checkPermession
//        if false then request perrmission on onRequestPermissionsResult

        if (!checkPermissionApp(this, PERMISSION_LIST))
            return;

        try {
            initComponentIfNeed();
            setAction();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Utils.getTagLog(MainActivity.this.getClass()), "onCreate: fail" + e.getMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
//                check again status permission after show dialog require
//                if not full perrmission require then show toast
                if (!checkStatusPermission(grantResults, PERMISSION_LIST)) {
                    Toast.makeText(MainActivity.this, "Cần cấp đủ quyền để thực hiện tác vụ này!", Toast.LENGTH_LONG).show();

                    //requirement again
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkPermissionApp(MainActivity.this, PERMISSION_LIST);
                        }
                    }, Toast.LENGTH_LONG);

                } else {
                    initComponentIfNeed();
                    setAction();
                }
                return;
            }
        }
    }

    //region BaseActivity
    @Override
    protected void initComponentIfNeed() {
    }

    @Override
    protected void setAction() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runAnimationView(MainActivity.this, getWindow().getDecorView().getRootView(), R.anim.twinking_view, TIME_DELAY);
                    Thread.sleep(TIME_DELAY);

//                    startActivity
                    startActivity(ListMediaActivity.newInstance(MainActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(getTagLog(this.getClass()), "run anim view error " + e.getMessage());
                }
            }
        }).start();

    }
    //endregion
}
