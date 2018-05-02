package vinhnb.gvn.com.playmedia.view.detailMedia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import vinhnb.gvn.com.playmedia.R;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.view.base.BasePresenter;

import static vinhnb.gvn.com.playmedia.util.Utils.ARG_POSITION_MEDIA;

public class ImageFragment extends Fragment implements ImageInteractor.View{
    private CallbackDetailMediaImageFragmentView mListener;
    private int mPos;
    private ImageInteractor.Presenter mIPresenter;

    @BindView(R.id.fragment_detail_media_image_iv)
    ImageView ivImageView;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(int position) {
        ImageFragment fragment = new ImageFragment();

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
        mIPresenter = new ImagePresenter(this);

//        check
//        if (mPos != ((BasePresenter) mIPresenter).getPositionMediaPlayingNow())
//            return null;

//        FileEntity fileEntity = mListener.getItemMediaFilePreparePlay();
        FileEntity fileEntity = ((BasePresenter) mIPresenter).getListMediaPlaying().get(mPos);
        File file = fileEntity.getmFile();
        if (!file.exists())
            Toast.makeText(getContext(), "File not found in sdcard", Toast.LENGTH_SHORT).show();
        Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ivImageView.setImageBitmap(imageBitmap);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackDetailMediaImageFragmentView) {
            mListener = (CallbackDetailMediaImageFragmentView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackDetailMediaImageFragmentView");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface CallbackDetailMediaImageFragmentView
//            extends ViewPageMediaInteractor
    {
        // TODO: Update argument type and name
    }
}
