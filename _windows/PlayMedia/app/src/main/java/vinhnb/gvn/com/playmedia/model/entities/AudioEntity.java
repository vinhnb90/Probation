package vinhnb.gvn.com.playmedia.model.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

public class AudioEntity extends FileEntity {
    private long positionTimePlay = 0;
    private String mNameMedia;
    private Bitmap mThumb;


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

    public AudioEntity( String mNameMedia, File file, Bitmap mThumb) {
        this.mFile = file;
        this.mNameMedia = mNameMedia;
        this.mThumb = mThumb;
    }

    public long getPositionTimePlay() {
        return positionTimePlay;
    }

    public Bitmap getmThumb() {
        return mThumb;
    }

    public void setmThumb(Bitmap mThumb) {
        this.mThumb = mThumb;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(positionTimePlay);
        dest.writeString(mNameMedia);
        dest.writeValue(mThumb);
    }


    protected AudioEntity(Parcel in) {
        positionTimePlay = in.readLong();
        mNameMedia = in.readString();
        mThumb = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<AudioEntity> CREATOR = new Creator<AudioEntity>() {
        @Override
        public AudioEntity createFromParcel(Parcel in) {
            return new AudioEntity(in);
        }

        @Override
        public AudioEntity[] newArray(int size) {
            return new AudioEntity[size];
        }
    };

}
