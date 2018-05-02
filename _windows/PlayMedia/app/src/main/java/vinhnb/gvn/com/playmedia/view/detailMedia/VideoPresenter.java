package vinhnb.gvn.com.playmedia.view.detailMedia;

import vinhnb.gvn.com.playmedia.model.sdard.IDataManager;
import vinhnb.gvn.com.playmedia.model.sdard.SdcardRepository;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

public class VideoPresenter extends BasePresenter implements VideoInteractor.Presenter {

    private IDataManager mIDataManager;
    private VideoInteractor.View mView;

    public VideoPresenter(VideoInteractor.View mView) {
        this.mView = mView;
        mIDataManager = SdcardRepository.getsInstance();
    }

}

interface VideoInteractor {
    interface View {

    }

    interface Presenter {
    }
}