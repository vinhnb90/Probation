package vinhnb.gvn.com.playmedia.view.detailMedia;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vinhnb.gvn.com.playmedia.R;
import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.util.Utils;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

public class AudioFragment extends Fragment implements AudioInteractor.View {
    private CallbackDetailMediaAudioFragmentView mCallback;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private AudioInteractor.Presenter mIPresenter;
    private int mPos;

    @BindView(R.id.fragment_detail_media_audio_tv_timebegin)
    TextView tvTimeBegin;

    @BindView(R.id.fragment_detail_media_audio_tv_timeend)
    TextView tvTimeEnd;

    @BindView(R.id.fragment_detail_media_audio_sbar_process)
    SeekBar sbarProcess;

    @BindView(R.id.fragment_detail_media_audio_ib_playing)
    ImageButton ibtnPlaying;

    @BindView(R.id.fragment_detail_media_audio_tv_audio_name)
    TextView tvAudioName;

    @BindView(R.id.fragment_detail_media_audio_tv_author_name)
    TextView tvAuthorName;


    public AudioFragment() {
        // Required empty public constructor
    }

    public static AudioFragment newInstance(int position) {
        AudioFragment fragment = new AudioFragment();
        Bundle args = new Bundle();
        args.putInt(Utils.ARG_POSITION_MEDIA, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPos = getArguments().getInt(Utils.ARG_POSITION_MEDIA);
        }

        mIPresenter = new AudioPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail_media_audio, container, false);
        ButterKnife.bind(this, view);


        initComponentIfNeed();

//        if (pos != ((BasePresenter) mIPresenter).getPositionMediaPlayingNow())
//            return null;

//        FileEntity fileEntity = mCallback.getItemMediaFilePreparePlay();
        FileEntity fileEntity = ((BasePresenter) mIPresenter).getListMediaPlaying().get(mPos);
        File file = fileEntity.getmFile();
        if (!file.exists())
            Toast.makeText(getContext(), "File not found in sdcard!", Toast.LENGTH_SHORT).show();

        mIPresenter.playAudioMedia((AudioEntity) fileEntity);


        return view;
    }

    private void initComponentIfNeed() {
        // Changing Button Image to pause image
        ibtnPlaying.setImageResource(R.drawable.btn_pause);

        // set Progress bar values
        sbarProcess.setProgress(0);
        sbarProcess.setMax(100);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackDetailMediaAudioFragmentView) {
            mCallback = (CallbackDetailMediaAudioFragmentView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackDetailMediaAudioFragmentView");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @OnClick(R.id.fragment_detail_media_audio_ib_playing)
    public void clickPlayPause(View view) {
        if (mIPresenter.isPlayingMedia()) {
            ibtnPlaying.setImageResource(R.drawable.btn_play);
            mIPresenter.pauseAudioMedia();
        } else {
            FileEntity fileEntity = mCallback.getItemMediaFilePreparePlay();
            ibtnPlaying.setImageResource(R.drawable.btn_pause);
            mIPresenter.resumeAudioMedia(((AudioEntity) fileEntity));
        }

    }


    @Override
    public void setAudioName(String name) {
        tvAudioName.setText(name);

    }

    @Override
    public void setAudioAuthorName(String name) {
        tvAuthorName.setText(name);
    }

    @Override
    public void setOnSeekBarChangeListener(AudioPresenter audioPresenter) {
        sbarProcess.setOnSeekBarChangeListener(audioPresenter);

    }

    @Override
    public void setTimeBegin(String time) {
        tvTimeBegin.setText(time);
    }

    @Override
    public void setTimeEnd(String time) {
        tvTimeEnd.setText(time);
    }

    @Override
    public void setProgressAudio(int progress) {
        sbarProcess.setProgress(progress);
    }


    public void onPauseMedia() {
        mIPresenter.pauseAudioMedia();

    }

    public void onStopMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

    }

    public interface CallbackDetailMediaAudioFragmentView extends ViewPageMediaInteractor {
        // TODO: Update argument type and name
    }

}
