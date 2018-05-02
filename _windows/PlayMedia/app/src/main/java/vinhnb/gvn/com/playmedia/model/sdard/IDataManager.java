package vinhnb.gvn.com.playmedia.model.sdard;

import android.support.annotation.IntRange;

import java.util.List;

import vinhnb.gvn.com.playmedia.model.entities.FileEntity;

public interface IDataManager {
    List<FileEntity> recursiveGetDataSdcard();

    void savePositionMedia(@IntRange(from = 0) int position);

    int getPositionMediaPlayingNow();

    List<FileEntity> getListMediaPlaying();

    void saveListMedia(List<FileEntity> fileEntity);

    FileEntity getItemMediaFilePreparePlay();

    void saveCurrentPauseMedia(long currentDuration);
}
