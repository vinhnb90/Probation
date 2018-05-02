package vinhnb.gvn.com.playmedia.view.detailMedia;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import java.io.IOException;

import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.sdard.IDataManager;
import vinhnb.gvn.com.playmedia.model.sdard.SdcardRepository;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

import static vinhnb.gvn.com.playmedia.util.Utils.getProgressPercentage;
import static vinhnb.gvn.com.playmedia.util.Utils.milliSecondsToTimer;
import static vinhnb.gvn.com.playmedia.util.Utils.progressToTimer;

public class AudioPresenter implements AudioInteractor.Presenter, SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mMediaPlayer;
    private AudioInteractor.View mView;
    private Handler mHandler = new Handler();
    private IDataManager mIDataManager;

    public AudioPresenter(AudioInteractor.View mView) {
        this.mView = mView;
        mIDataManager = SdcardRepository.getsInstance();
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mMediaPlayer.getDuration();
            long currentDuration = mMediaPlayer.getCurrentPosition();

            mView.setTimeBegin(milliSecondsToTimer(currentDuration) + "");
            mView.setTimeEnd(milliSecondsToTimer(totalDuration) + "");

            // Updating progress bar
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            mView.setProgressAudio(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void playAudioMedia(AudioEntity audioEntity) {
        // Play song
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(audioEntity.getmFile().getAbsolutePath());
            mMediaPlayer.prepare();
            mMediaPlayer.setLooping(true);
            mMediaPlayer.seekTo((int) audioEntity.getCurrentPausePlay());
            //title
            mView.setAudioName(audioEntity.getmNameMedia());
            mView.setAudioAuthorName("Author");
            //set seek bar

            mView.setOnSeekBarChangeListener(this);

            mMediaPlayer.start();

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseAudioMedia() {
        mMediaPlayer.pause();
        long currentDuration = mMediaPlayer.getCurrentPosition();
        mIDataManager.saveCurrentPauseMedia(currentDuration);
    }

    @Override
    public void resumeAudioMedia(AudioEntity fileEntity) {
        if (mMediaPlayer == null)
            playAudioMedia(fileEntity);
        else {
            mMediaPlayer.seekTo((int) fileEntity.getCurrentPausePlay());
            mMediaPlayer.start();
        }
    }

    @Override
    public boolean isPlayingMedia() {
        return mMediaPlayer.isPlaying();
    }


    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    //region SeekBar.OnSeekBarChangeListener
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mMediaPlayer.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mMediaPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mMediaPlayer.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mMediaPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    //endregion
}

interface AudioInteractor {
    interface View {

        void setAudioName(String name);

        void setAudioAuthorName(String name);

        void setOnSeekBarChangeListener(AudioPresenter audioPresenter);

        void setTimeBegin(String time);

        void setTimeEnd(String time);

        void setProgressAudio(int progress);
    }

    interface Presenter {
        void playAudioMedia(AudioEntity file);

        void pauseAudioMedia();

        void resumeAudioMedia(AudioEntity fileEntity);

        boolean isPlayingMedia();
    }

}