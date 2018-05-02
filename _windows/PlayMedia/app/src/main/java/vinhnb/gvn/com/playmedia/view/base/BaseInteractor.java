package vinhnb.gvn.com.playmedia.view.base;

import java.util.List;

import vinhnb.gvn.com.playmedia.model.entities.FileEntity;

public interface BaseInteractor {
    interface Presenter {
        int getPositionMediaPlayingNow();

        void savePositionMediaPlayingNow(int position);

        List<FileEntity> getListMediaPlaying();
    }

    interface View {

    }
}