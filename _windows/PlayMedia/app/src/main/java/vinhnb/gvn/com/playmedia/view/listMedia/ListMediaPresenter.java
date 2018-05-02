package vinhnb.gvn.com.playmedia.view.listMedia;

import android.support.annotation.NonNull;

import java.util.List;

import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.model.sdard.IDataManager;
import vinhnb.gvn.com.playmedia.model.sdard.SdcardRepository;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

public class ListMediaPresenter extends BasePresenter implements ListMediaInteractor.Presenter {
    private ListMediaInteractor.View view;
    private IDataManager mISdcardModel;

    public ListMediaPresenter(ListMediaInteractor.View view) {
        this.view = view;

        mISdcardModel =  SdcardRepository.getsInstance();
    }

    @Override
    public void loadDataSdcard() {
        List<FileEntity> data = mISdcardModel.recursiveGetDataSdcard();

        if (data.size() != 0)
            view.fillDataList(data);
        else
            view.showMessageWarning("Không có dữ liệu!");
    }

    @Override
    public void saveListMedia(List<FileEntity> fileEntity) {
        mISdcardModel.saveListMedia(fileEntity);
    }

    @Override
    public void savePositionMedia(int position) {
        mISdcardModel.savePositionMedia(position);
    }
}

interface ListMediaInteractor {
    interface View {
        void fillDataList(List<FileEntity> data);

        void showMessageWarning(@NonNull String message);

        void requireAgainPermissionToLoadData();
    }

    interface Presenter {
        void loadDataSdcard();

        void saveListMedia(List<FileEntity> fileEntity);

        void savePositionMedia(int position);
    }
}

