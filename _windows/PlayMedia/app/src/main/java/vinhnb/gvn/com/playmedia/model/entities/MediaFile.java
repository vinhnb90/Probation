package vinhnb.gvn.com.playmedia.model.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.io.File;
import java.net.URLConnection;

import vinhnb.gvn.com.playmedia.util.MediaPlayAppUtils;
import vinhnb.gvn.com.playmedia.util.Utils;

public class MediaFile {
    public static FileEntity getFileEntity(File file) {

        //check file
        if (file == null)
            return null;

        if (!file.isFile())
            return null;

        //if image
        if (isImageFile(file.getPath())) {
            return new ImageEntity(file.getName(), file);
        }

        if (isVideoFile(file.getPath())) {
            return new VideoEntity(file.getName(), file, Utils.WIDTH_MEDIA_DEFAULT, Utils.HEIGHT_MEDIA_DEFAULT);
        }

        if (isAudioFile(file.getPath())) {
            return new AudioEntity(file.getName(), file);
        }

        return null;
    }

    private static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    private static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    private static boolean isAudioFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("audio");
    }

    private Bitmap getThumbnailAudioFile(File file) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        byte[] rawArt;
        BitmapFactory.Options bfo = new BitmapFactory.Options();

        mmr.setDataSource(MediaPlayAppUtils.getContext(), Uri.fromFile(file));
        rawArt = mmr.getEmbeddedPicture();

        // if rawArt is null then no cover art is embedded in the file or is not

        Bitmap art = null;
        if (null != rawArt)
            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);

        return art;
    }
}
