package vinhnb.gvn.com.playmedia.view.base;

import java.util.List;

import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.model.sdard.IDataManager;
import vinhnb.gvn.com.playmedia.model.sdard.SdcardRepository;

public class BasePresenter implements BaseInteractor.Presenter {
    private IDataManager mISdcardModel;

    public BasePresenter() {
        this.mISdcardModel = SdcardRepository.getsInstance();
    }

    @Override
    public int getPositionMediaPlayingNow() {
        return mISdcardModel.getPositionMediaPlayingNow();
    }

    @Override
    public List<FileEntity> getListMediaPlaying() {
        return mISdcardModel.getListMediaPlaying();
    }

    public FileEntity getItemMediaFilePreparePlay() {
        return mISdcardModel.getItemMediaFilePreparePlay();
    }
}

interface BaseInteractor {
    interface Presenter {
        int getPositionMediaPlayingNow();

        List<FileEntity> getListMediaPlaying();
    }
}
