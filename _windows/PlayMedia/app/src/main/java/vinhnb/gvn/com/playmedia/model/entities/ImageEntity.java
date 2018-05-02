package vinhnb.gvn.com.playmedia.model.entities;

import java.io.File;

public class ImageEntity extends FileEntity{
    private String mNameImage;

    public ImageEntity(String mNameImage, File mFile) {
        this.mNameImage = mNameImage;
        this.mFile = mFile;
    }

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
}
