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
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.music.player.ListType;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.utils.FileUtil;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class AllSongAdapter extends BaseMusicAdapter {

    private LayoutInflater mLayoutInflater;
    private List<SongInfo> mSongs;

    private long[] mSongIds;

    private void  getSongIds(){
        if (mSongs != null){
            int size = mSongs.size();
            mSongIds = new long[size];
            for (int i = 0; i < size; i ++){
                SongInfo songInfo = mSongs.get(i);
                mSongIds[i] = songInfo.getId();
            }
        }
    }

    public AllSongAdapter(Context context, List<SongInfo> songs) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mSongs = songs;
        getSongIds();
    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_music_layout, null);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SongHolder songHolder = (SongHolder) holder;

        SongInfo songInfo = mSongs.get(position);

        songHolder.indexView.setText(String.valueOf(position + 1));
        try {
            ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(songInfo.getAlbumId()).toString(),
                    songHolder.albumIconView, new DisplayImageOptions.Builder().cacheInMemory(true)
                            .showImageOnLoading(R.drawable.ic_empty_music2)
                            .resetViewBeforeLoading(true).build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        songHolder.songNameView.setText(songInfo.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FileUtil.formatFileSize(0, songInfo.getSize()));
        stringBuilder.append(" ");
        stringBuilder.append(songInfo.getArtist());
        stringBuilder.append("-");
        stringBuilder.append(songInfo.getAlbumName());
        songHolder.songInfoView.setText(stringBuilder);   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.playList(mSongIds, position, ListType.Song);
            }
        });
    }

    private static class SongHolder extends RecyclerView.ViewHolder {

        TextView indexView;
        ImageView albumIconView;
        TextView songNameView;
        TextView songInfoView;
        ImageView songOprView;

        public SongHolder(View itemView) {
            super(itemView);
            indexView = itemView.findViewById(R.id.item_music_index_view);
            albumIconView = itemView.findViewById(R.id.item_music_album_view);
            songNameView = itemView.findViewById(R.id.item_music_name_view);
            songInfoView = itemView.findViewById(R.id.item_music_artist_view);
            songOprView = itemView.findViewById(R.id.item_music_pro_view);
        }
    }
}