package vinhnb.gvn.com.playmedia.view.listMedia;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vinhnb.gvn.com.playmedia.R;
import vinhnb.gvn.com.playmedia.model.entities.AudioEntity;
import vinhnb.gvn.com.playmedia.model.entities.FileEntity;
import vinhnb.gvn.com.playmedia.model.entities.ImageEntity;
import vinhnb.gvn.com.playmedia.model.entities.VideoEntity;
import vinhnb.gvn.com.playmedia.util.MediaPlayAppUtils;
import vinhnb.gvn.com.playmedia.util.Utils;

public class ListMediaAdapter extends RecyclerView.Adapter<ListMediaAdapter.MediaFileViewHolder> {
    private static List<FileEntity> mListData = new ArrayList<>();
    private final Drawable mIconImage;
    private final Drawable mIconVideo;
    private final Drawable mIconAudio;
    private final Drawable mIconFolder;
    private Context mContext;
    private static ListMediaCallback mCallback;

    public ListMediaAdapter(List<FileEntity> mListData, Context mContext, ListMediaCallback mCallback) {
        this.mListData = mListData;
        this.mContext = mContext;
        this.mCallback = mCallback;

        mIconImage = MediaPlayAppUtils.getContext().getResources().getDrawable(R.drawable.ic_image);
        mIconVideo = MediaPlayAppUtils.getContext().getResources().getDrawable(R.drawable.ic_video);
        mIconAudio = MediaPlayAppUtils.getContext().getResources().getDrawable(R.drawable.ic_audio);
        mIconFolder = MediaPlayAppUtils.getContext().getResources().getDrawable(R.drawable.ic_folder);
    }

    @NonNull
    @Override
    public MediaFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.activity_list_media_row, null);
        return new MediaFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaFileViewHolder holder, int position) {
        FileEntity fileEntity = mListData.get(position);


        if (fileEntity instanceof ImageEntity) {
            holder.tvTitle.setText(((ImageEntity) fileEntity).getmNameImage());
            holder.tvContent.setText("Image");
            holder.ivIcon.setImageDrawable(mIconImage);
        }

        if (fileEntity instanceof AudioEntity) {
            holder.tvTitle.setText(((AudioEntity) fileEntity).getmNameMedia());
            holder.tvContent.setText("Audio");
            holder.ivIcon.setImageDrawable(mIconAudio);
        }

        if (fileEntity instanceof VideoEntity) {
            holder.tvTitle.setText(((VideoEntity) fileEntity).getmNameMedia());
            holder.tvContent.setText("Video");
            holder.ivIcon.setImageDrawable(mIconVideo);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    /*
     * listener
     * */
    public interface ListMediaCallback {
        void doClickRow(FileEntity fileEntity, int position);
    }

    /*
     * func
     * */


    /*
     * instance
     * */

    public static class MediaFileViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvContent;

        public MediaFileViewHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.activity_list_media_row_iv_file);
            tvTitle = itemView.findViewById(R.id.activity_list_media_row_tv_title_file);
            tvContent = itemView.findViewById(R.id.activity_list_media_row_tv_content_file);

            itemView.findViewById(R.id.activity_list_media_row).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCallback.doClickRow(mListData.get(position), position);
                }
            });
        }
    }
}
