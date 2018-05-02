package vinhnb.gvn.com.playmedia.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class ImageEntity extends FileEntity {
    private String mNameImage;

    public ImageEntity(String mNameImage, File mFile) {
        this.mNameImage = mNameImage;
        this.mFile = mFile;
    }

    protected ImageEntity(Parcel in) {
        mNameImage = in.readString();
    }

    public static final Creator<ImageEntity> CREATOR = new Creator<ImageEntity>() {
        @Override
        public ImageEntity createFromParcel(Parcel in) {
            return new ImageEntity(in);
        }

        @Override
        public ImageEntity[] newArray(int size) {
            return new ImageEntity[size];
        }
    };

    public String getmNameImage() {
        return mNameImage;
    }

    public void setmNameImage(String mNameImage) {
        this.mNameImage = mNameImage;
    }

    public File getmFile() {
        return mFile;
    }

    public void setmFile(File mFile) {
        this.mFile = mFile;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNameImage);
    }
}
