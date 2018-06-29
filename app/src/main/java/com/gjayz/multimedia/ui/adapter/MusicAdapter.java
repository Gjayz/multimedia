package com.gjayz.multimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.media.MediaInfoUtil;
import com.gjayz.multimedia.music.bean.MusicInfo;
import com.gjayz.multimedia.utils.FileUtil;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    private Context mContext;
    private List<MusicInfo> mMusicInfos;

    public interface OnItemClickListener{
        void onItemClick(View v, int postion);
    }

    private OnItemClickListener mOnItemClickListener;

    public MusicAdapter(Context context, List<MusicInfo> musicInfos){
        mContext = context;
        mMusicInfos = musicInfos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music_layout, null);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, final int position) {
        MusicInfo musicInfo = mMusicInfos.get(position);
        holder.mMusicIndexView.setText(String.valueOf(position + 1));
        holder.mMusicAlbumView.setImageBitmap(MediaInfoUtil.getArtwork(mContext, musicInfo.getId(), musicInfo.getAlbumId(), false, true));
        holder.mMusicNameView.setText(musicInfo.getTitle());
        holder.mMusicArtistView.setText(FileUtil.formatFileSize(0, musicInfo.getSize()) + " " + musicInfo.getArtist() + " - " + musicInfo.getAlbumName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMusicInfos == null ? 0 : mMusicInfos.size();
    }

    class MusicHolder extends RecyclerView.ViewHolder {

        TextView mMusicIndexView;
        ImageView mMusicAlbumView;
        TextView mMusicNameView;
        TextView mMusicArtistView;
        ImageView mMusicProImageView;

        public MusicHolder(View itemView) {
            super(itemView);
            mMusicIndexView = itemView.findViewById(R.id.item_music_index_view);
            mMusicAlbumView = itemView.findViewById(R.id.item_music_album_view);
            mMusicNameView = itemView.findViewById(R.id.item_music_name_view);
            mMusicArtistView = itemView.findViewById(R.id.item_music_artist_view);
            mMusicProImageView = itemView.findViewById(R.id.item_music_pro_view);
        }
    }
}