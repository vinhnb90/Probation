package vinhnb.gvn.com.playmedia.view.detailMedia;

import vinhnb.gvn.com.playmedia.model.sdard.IDataManager;
import vinhnb.gvn.com.playmedia.model.sdard.SdcardRepository;
import vinhnb.gvn.com.playmedia.view.base.BaseInteractor;

public class DetailMediaPresenter implements DetailMediaInteractor.Presenter {
    private DetailMediaInteractor.View mView;
    private IDataManager mIDataManager;

    public DetailMediaPresenter(DetailMediaInteractor.View mView) {
        this.mView = mView;
        mIDataManager = SdcardRepository.getsInstance();
    }
}

interface DetailMediaInteractor {
    interface View {

    }

    interface Presenter extends BaseInteractor {
    }

}
