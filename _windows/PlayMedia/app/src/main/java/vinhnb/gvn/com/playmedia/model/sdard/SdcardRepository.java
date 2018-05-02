package vinhnb.gvn.com.playmedia.model.sdard;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntRange;
import android.util.Log;

import org.w3c.dom.Entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.model.entities.MediaFile;
import vinhnb.gvn.com.playmedia.model.entities.VideoEntity;
import vinhnb.gvn.com.playmedia.util.MediaPlayAppUtils;
import vinhnb.gvn.com.playmedia.util.Utils;

public class SdcardRepository implements IDataManager {
    private static SdcardRepository sInstance;
    private int mPosItemPlaying;
    private List<FileEntity> mListFile = new ArrayList<>();

    private SdcardRepository() {
    }

    public static synchronized SdcardRepository getsInstance() {
        if (sInstance == null)
            sInstance = new SdcardRepository();
        return sInstance;
    }

    //region IDataManager
    @Override
    public List<FileEntity> recursiveGetDataSdcard() {
        //query cursor collum data
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Audio.AudioColumns.DATA, MediaStore.Video.VideoColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        List<FileEntity> resultMedia = new ArrayList<>();

        //get contentReslover of Uri external
        String[] directories = null;
        if (u != null) {
            c = MediaPlayAppUtils.getContext().getContentResolver().query(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst())) {
            do {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try {
                    dirList.add(tempDir);
                } catch (Exception e) {
                    //nothing if has ex add to TreeSet
                }
            }
            while (c.moveToNext());

            //to array
            directories = new String[dirList.size()];
            dirList.toArray(directories);
        }

        for (int i = 0; i < dirList.size(); i++) {
            File dir = new File(directories[i]);
            File[] listFiles = dir.listFiles();

            if (listFiles == null)
                continue;

            for (File file : listFiles) {
                try {
                    if (file.isDirectory()) {
                        listFiles = file.listFiles();
                    }

                    //check
                    FileEntity fileEntity = MediaFile.getFileEntity(file);
                    if (fileEntity != null)
                        resultMedia.add(fileEntity);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(Utils.getTagLog(SdcardRepository.class), "recursiveGetDataSdcard: " + e.getMessage());
                }
            }
        }

        return resultMedia;
    }

    @Override
    public void savePositionMedia(@IntRange(from = 0) int position) {
        mPosItemPlaying = position;
    }

    @Override
    public int getPositionMediaPlayingNow() {
        return mPosItemPlaying;
    }

    @Override
    public List<FileEntity> getListMediaPlaying() {
        return mListFile;
    }

    @Override
    public void saveListMedia(List<FileEntity> fileEntity) {
        mListFile = fileEntity;
    }

    @Override
    public FileEntity getItemMediaFilePreparePlay() {
        return mListFile.get(mPosItemPlaying);
    }

    @Override
    public void saveCurrentPauseMedia(long currentDuration) {
        FileEntity fileEntity = mListFile.get(mPosItemPlaying);
        if (fileEntity instanceof AudioEntity)
            ((AudioEntity) mListFile.get(mPosItemPlaying)).setPositionTimePlay(currentDuration);

        if (fileEntity instanceof VideoEntity)
            ((VideoEntity) mListFile.get(mPosItemPlaying)).setPositionTimePlay(currentDuration);
    }

    //endregion


}
