package vinhnb.gvn.com.playmedia.model.entities;

import android.graphics.Bitmap;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

public class AudioEntity extends FileEntity {
    private long positionTimePlay = 0;
    private String mNameMedia;

    public AudioEntity(String mNameMedia, @NonNull File mFile) {
        this.mNameMedia = mNameMedia;
        this.mFile = mFile;
    }

    public long getCurrentPausePlay() {
        return positionTimePlay;
    }

    public void setPositionTimePlay(long positionTimePlay) {
        this.positionTimePlay = positionTimePlay;
    }

    public String getmNameMedia() {
        return mNameMedia;
    }

    public void setmNameMedia(String mNameMedia) {
        this.mNameMedia = mNameMedia;
    }

    public File getmFile() {
        return mFile;
    }

    public void setmFile(File mFile) {
        this.mFile = mFile;
    }
}
