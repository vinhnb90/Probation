package vinhnb.gvn.com.playmedia.view.listMedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vinhnb.gvn.com.playmedia.R;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.view.base.BaseActivity;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;
import vinhnb.gvn.com.playmedia.view.detailMedia.DetailMediaActivity;

import static vinhnb.gvn.com.playmedia.util.Utils.REQUEST_CODE_DETAIL_MEDIA;
import static vinhnb.gvn.com.playmedia.util.Utils.REQUEST_CODE_PERMISSION;
import static vinhnb.gvn.com.playmedia.util.Utils.checkPermissionApp;
import static vinhnb.gvn.com.playmedia.util.Utils.checkStatusPermission;
import static vinhnb.gvn.com.playmedia.view.home.MainActivity.PERMISSION_LIST;

public class ListMediaActivity extends BaseActivity implements ListMediaInteractor.View {

    /*
     * var
     * */
    @BindView(R.id.activity_list_media_rv_list_media)
    RecyclerView rvListMedia;

    private ListMediaInteractor.Presenter mIListMediaPresenter;
    private Unbinder mUnbinder;

    /*
     * instance
     * */
    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, ListMediaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /*
     * life cycle
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_media);

        setupPresenter();

        initComponentIfNeed();

        setAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_list_media_appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load_sdcard:
                requireAgainPermissionToLoadData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
//                check again status permission after show dialog require
//                if not full perrmission require then show toast
                if (!checkStatusPermission(grantResults, PERMISSION_LIST)) {
                    Toast.makeText(ListMediaActivity.this, "Unable to show permission required", Toast.LENGTH_LONG).show();
                    Log.e(getClass().getName(), "Unable to show permission required");
                } else {
                    mIListMediaPresenter.loadDataSdcard();
                }
                return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (rvListMedia.getAdapter() instanceof ListMediaAdapter) {
            int positionNowPlay = ((BasePresenter) mIListMediaPresenter).getPositionMediaPlayingNow();
            rvListMedia.scrollToPosition(positionNowPlay);
            rvListMedia.invalidate();
        }
    }

    /*
     * function
     * */
    private void setupPresenter() {
        mIListMediaPresenter = new ListMediaPresenter(this);
    }


    /*
     * listeners
     * */


    //region BaseActivity
    @Override
    protected void initComponentIfNeed() {
        //bind
        mUnbinder = ButterKnife.bind(this);

        //recycle
        rvListMedia.setLayoutManager(new LinearLayoutManager(this));
        rvListMedia.hasFixedSize();
    }

    @Override
    protected void setAction() {
        this.requireAgainPermissionToLoadData();
    }
    //endregion

    //region BaseInteractor.View
    @Override
    public void fillDataList(final List<FileEntity> data) {
        rvListMedia.setAdapter(new ListMediaAdapter(data, this, new ListMediaAdapter.ListMediaCallback() {
            @Override
            public void doClickRow(FileEntity fileEntity, int position) {
                mIListMediaPresenter.saveListMedia(data);
                mIListMediaPresenter.savePositionMedia(position);

                Intent intent = DetailMediaActivity.createInstance(ListMediaActivity.this, data, position);
                startActivityForResult(intent, REQUEST_CODE_DETAIL_MEDIA);
            }
        }));

        rvListMedia.invalidate();
    }

    @Override
    public void showMessageWarning(@NonNull String message) {
        rvListMedia.removeAllViews();
        rvListMedia.invalidate();

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requireAgainPermissionToLoadData() {
        //check permission
        if (!checkPermissionApp(this, PERMISSION_LIST))
            return;

        mIListMediaPresenter.loadDataSdcard();
//            Toast.makeText(this, "Requirement permission to load data!", Toast.LENGTH_SHORT).show();
    }
    //endregion
}
