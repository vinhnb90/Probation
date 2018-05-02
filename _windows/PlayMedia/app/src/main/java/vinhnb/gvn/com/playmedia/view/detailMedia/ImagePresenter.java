package vinhnb.gvn.com.playmedia.view.detailMedia;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import java.io.IOException;

import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.sdard.IDataManager;
import vinhnb.gvn.com.playmedia.model.sdard.SdcardRepository;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

public class ImagePresenter extends BasePresenter implements ImageInteractor.Presenter{

    private IDataManager mIDataManager;
    private ImageInteractor.View mView;

    public ImagePresenter(ImageInteractor.View mView) {
        this.mView = mView;
        mIDataManager = SdcardRepository.getsInstance();
    }

}

interface ImageInteractor {
    interface View {

    }

    interface Presenter {
    }
}