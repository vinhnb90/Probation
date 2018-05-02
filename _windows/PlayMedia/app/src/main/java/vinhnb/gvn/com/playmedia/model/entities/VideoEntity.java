package vinhnb.gvn.com.playmedia.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;

import java.io.File;

public class VideoEntity extends FileEntity{
    private long positionTimePlay = 0;
    private String mNameMedia;
    private int mWidth;
    private int mHeight;

    public VideoEntity(String mNameMedia, File mFile, @IntRange(from = 0) int mWidth, @IntRange(from = 0) int mHeight) {
        this.mNameMedia = mNameMedia;
        this.mFile = mFile;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }

    protected VideoEntity(Parcel in) {
        positionTimePlay = in.readLong();
        mNameMedia = in.readString();
        mWidth = in.readInt();
        mHeight = in.readInt();
    }

    public static final Creator<VideoEntity> CREATOR = new Creator<VideoEntity>() {
        @Override
        public VideoEntity createFromParcel(Parcel in) {
            return new VideoEntity(in);
        }

        @Override
        public VideoEntity[] newArray(int size) {
            return new VideoEntity[size];
        }
    };

    public long getPositionTimePlay() {
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

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(positionTimePlay);
        dest.writeString(mNameMedia);
        dest.writeInt(mWidth);
        dest.writeInt(mHeight);
    }
}
