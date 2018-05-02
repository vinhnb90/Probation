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
import vinhnb.gvn.com.playmedia.R;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

import static vinhnb.gvn.com.playmedia.util.Utils.ARG_POSITION_MEDIA;

public class VideoFragment extends  Fragment implements VideoInteractor.View {
    private CallbackDetailMediaVideoFragmentView mListener;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler;
    private VideoInteractor.Presenter mIPresenter;
    private int mPos;

    @BindView(R.id.fragment_detail_media_video_tv_timebegin)
    TextView tvTimeBegin;

    @BindView(R.id.fragment_detail_media_video_tv_timeend)
    TextView tvTimeEnd;

    @BindView(R.id.fragment_detail_media_video_sbar_process)
    SeekBar sbarProcess;

    @BindView(R.id.fragment_detail_media_video_ib_playing)
    ImageButton ibtnPlaying;

    @BindView(R.id.fragment_detail_media_video_tv_video_name)
    TextView tvVideoName;

    @BindView(R.id.fragment_detail_media_video_tv_author_name)
    TextView tvAuthorName;


    public VideoFragment() {
        // Required empty public constructor
    }


    public static VideoFragment newInstance(int position) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION_MEDIA, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPos = getArguments().getInt(ARG_POSITION_MEDIA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_media_image, container, false);
        ButterKnife.bind(this, view);

        mIPresenter = new VideoPresenter(this);

//        FileEntity fileEntity = mListener.getItemMediaFilePreparePlay();
        FileEntity fileEntity = ((BasePresenter) mIPresenter).getListMediaPlaying().get(mPos);
        File file = fileEntity.getmFile();
        if (!file.exists())
            Toast.makeText(getContext(), "File not found in sdcard!", Toast.LENGTH_SHORT).show();

//        check
//        if (mPos != ((BasePresenter) mIPresenter).getPositionMediaPlayingNow())
//            return null;

//        try {
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.reset();
//            mMediaPlayer.setDataSource(file.getAbsolutePath());
//            mMediaPlayer.prepare();
//            mMediaPlayer.setLooping(true);
//
//            //title
//            tvVideoName.setText(file.getName());
////            tvAuthorName.setText();
//            //set seek bar
//
//            sbarProcess.setMax(mMediaPlayer.getDuration());
//            sbarProcess.setOnSeekBarChangeListener(this);
//            mMediaPlayer.setOnCompletionListener(this);
//
//            mMediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getContext(), "File can not play!", Toast.LENGTH_SHORT).show();
//        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackDetailMediaVideoFragmentView) {
            mListener = (CallbackDetailMediaVideoFragmentView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackDetailMediaVideoFragmentView");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onPauseMedia() {
    }

    public void onStopMedia() {
    }

    /*
     * func
     * */


    public interface CallbackDetailMediaVideoFragmentView
//            extends ViewPageMediaInteractor
    {
        // TODO: Update argument type and name
    }

}
