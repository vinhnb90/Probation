package vinhnb.gvn.com.playmedia.view.detailMedia;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.model.entities.ImageEntity;
import vinhnb.gvn.com.playmedia.model.entities.VideoEntity;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

public class ViewPageMediaAdapter extends FragmentStatePagerAdapter {
    private List<FileEntity> mData = new ArrayList<>();
    private CallbackDetailMediaViewViewPageAdapter mCallback;

//    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    private Context mContext;

    public ViewPageMediaAdapter(FragmentManager fm, List<FileEntity> mData, Context context) {
        super(fm);
        this.mData.addAll(mData);
        this.mContext = context;
        if (context instanceof CallbackDetailMediaViewViewPageAdapter) {
            mCallback = (CallbackDetailMediaViewViewPageAdapter) this.mContext;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackDetailMediaAudioFragmentView");
        }
    }


    @Override
    public Fragment getItem(int position) {
        FileEntity fileEntity = mData.get(position);

        if (fileEntity instanceof ImageEntity)
            return ImageFragment.newInstance(position);

        if (fileEntity instanceof AudioEntity)
            return AudioFragment.newInstance(position);

        if (fileEntity instanceof VideoEntity)
            return VideoFragment.newInstance(position);

        return null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        registeredFragments.put(position, fragment);
//        return fragment;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        registeredFragments.remove(position);
//        super.destroyItem(container, position, object);
//    }
//
//    public Fragment getRegisteredFragment(int position) {
//        return registeredFragments.get(position);
//    }

    public interface CallbackDetailMediaViewViewPageAdapter
//            extends ViewPageMediaInteractor
    {

    }

}


