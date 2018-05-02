package vinhnb.gvn.com.playmedia.view.detailMedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vinhnb.gvn.com.playmedia.R;
import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.model.entities.ImageEntity;
import vinhnb.gvn.com.playmedia.model.entities.VideoEntity;
import vinhnb.gvn.com.playmedia.util.Utils;
import vinhnb.gvn.com.playmedia.view.base.BaseActivity;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

public class DetailMediaActivity extends BaseActivity implements
//        DetailMediaInteractor.View,
        ImageFragment.CallbackDetailMediaImageFragmentView,
        VideoFragment.CallbackDetailMediaVideoFragmentView,
        AudioFragment.CallbackDetailMediaAudioFragmentView,
        ViewPageMediaAdapter.CallbackDetailMediaViewViewPageAdapter,
        ViewPager.OnPageChangeListener,
        DetailMediaInteractor.View {
    /*
     * var
     * */

    @BindView(R.id.activity_detail_list_media_viewpage)
    ViewPager mViewPager;

    private DetailMediaInteractor.Presenter mIPresenter;
    private Unbinder mUnbinder;
    private ViewPageMediaAdapter mViewPageMediaAdapter;
    private int mPositionPlaying;
    private List<FileEntity> mListMedia = new ArrayList<>();

    /*
     * instance
     * */

    public static Intent createInstance(Context context, List<FileEntity> data, int positionPlaying) {
        Intent intent = new Intent(context, DetailMediaActivity.class);
        intent.putExtra(Utils.INTENT_CODE_DETAIL_MEDIA_POSITION_PLAY_NOW, positionPlaying);
        intent.putParcelableArrayListExtra(Utils.INTENT_CODE_DETAIL_MEDIA_LIST, (ArrayList<? extends Parcelable>) data);
        return intent;
    }

    /*
     * lifecycle
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_media);

        try {
            initComponentIfNeed();
            setAction();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Utils.getTagLog(DetailMediaActivity.this.getClass()), "onCreate: fail" + e.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_detail_media_appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //when click menu icon nav up
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
//        stopMedia();
    }


    //region BaseActivity
    @Override
    protected void initComponentIfNeed() {
        mUnbinder = ButterKnife.bind(this);

        mIPresenter = new DetailMediaPresenter(this);
    }

    @Override
    protected void setAction() {
//        setup nav up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);


//        get bundle
        mPositionPlaying = getIntent().getIntExtra(Utils.INTENT_CODE_DETAIL_MEDIA_POSITION_PLAY_NOW, 0);
        mListMedia = getIntent().getParcelableArrayListExtra(Utils.INTENT_CODE_DETAIL_MEDIA_LIST);

//        save position
//        mIPresenter.savePositionMedia(mPositionPlaying);

//        viewpage
//        List<FileEntity> data = ((BasePresenter) mIPresenter).getListMediaPlaying();
//        int posItemPlaying = ((BasePresenter) mIPresenter).getPositionMediaPlayingNow();
        mViewPageMediaAdapter = new ViewPageMediaAdapter(getSupportFragmentManager(), mListMedia, this);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(mPositionPlaying);
        mViewPager.setAdapter(mViewPageMediaAdapter);

//        mViewPager.setPageTransformer(true, new ViewPageZoomOutPageTransformer());
    }


//    @Override
//    public FileEntity getItemMediaFilePreparePlay() {
//        return ((BasePresenter) mIPresenter).getItemMediaFilePreparePlay();
//    }

//endregion


    //region ViewPager.OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i("onPageScrolled", "onPageScrolled: ");
    }

    @Override
    public void onPageSelected(int position) {
        pauseMedia();
        mPositionPlaying = position;

        //save
        ((BasePresenter) mIPresenter).savePositionMediaPlayingNow(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i("onPageScrolled", "onPageScrolled: ");
    }

    //endregion

    private void stopMedia() {
        //       getCurrent faragment
//        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.activity_detail_list_media_viewpage + ":" + mViewPager.getCurrentItem());
//        if (mViewPager.getCurrentItem() == 0 && page != null) {
        //check
        Fragment fragment = ((ViewPageMediaAdapter) mViewPager.getAdapter()).getItem(this.mPositionPlaying);
        FileEntity fileEntity = mListMedia.get(this.mPositionPlaying);

//        FileEntity fileEntity = getItemMediaFilePreparePlay();
        if (fileEntity instanceof ImageEntity) {
            //nothing
        }

        if (fileEntity instanceof AudioEntity) {
            ((AudioFragment) fragment).onStopMedia();
        }

        if (fileEntity instanceof VideoEntity) {
            ((VideoFragment) fragment).onStopMedia();
        }
//        }

    }

    private void pauseMedia() {
        //check
//        Fragment fragment = ((ViewPageMediaAdapter) mViewPager.getAdapter()).getRegisteredFragment(((BasePresenter) mIPresenter).getPositionMediaPlayingNow());

        Fragment fragment = ((ViewPageMediaAdapter) mViewPager.getAdapter()).getItem(this.mPositionPlaying);
        FileEntity fileEntity = mListMedia.get(this.mPositionPlaying);
        if (fileEntity instanceof ImageEntity) {
            //nothing
        }

        if (fileEntity instanceof AudioEntity) {
            ((AudioFragment) fragment).onPauseMedia();
        }

        if (fileEntity instanceof VideoEntity) {
            ((VideoFragment) fragment).onPauseMedia();
        }
    }
}
