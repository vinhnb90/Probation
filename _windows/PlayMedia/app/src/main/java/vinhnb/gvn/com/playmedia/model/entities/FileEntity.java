package vinhnb.gvn.com.playmedia.model.entities;

import android.os.Parcelable;

import java.io.File;

public abstract class FileEntity implements Parcelable{
    protected File mFile;


    public File getmFile() {
        return mFile;
    }

    public void setmFile(File mFile) {
        this.mFile = mFile;
    }
}
