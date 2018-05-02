package vinhnb.gvn.com.playmedia.view.detailMedia;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import java.io.IOException;

import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.sdard.IDataManager;
import vinhnb.gvn.com.playmedia.model.sdard.SdcardRepository;

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


    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
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