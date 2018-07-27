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

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    private Context mContext;
    private List<SongInfo> mMusicInfos;
    private long[] mSongIds;

    private void  getSongIds(){
        if (mMusicInfos != null){
            int size = mMusicInfos.size();
            mSongIds = new long[size];
            for (int i = 0; i < size; i ++){
                SongInfo songInfo = mMusicInfos.get(i);
                mSongIds[i] = songInfo.getId();
            }
        }
    }

    public MusicAdapter(Context context, List<SongInfo> musicInfos) {
        mContext = context;
        mMusicInfos = musicInfos;
        getSongIds();
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music_layout, null);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, final int position) {
        SongInfo musicInfo = mMusicInfos.get(position);
        holder.mMusicIndexView.setText(String.valueOf(position + 1));
        try {
            ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(musicInfo.getAlbumId()).toString(),
                    holder.mMusicAlbumView, new DisplayImageOptions.Builder().cacheInMemory(true)
                            .showImageOnLoading(R.drawable.ic_empty_music2)
                            .resetViewBeforeLoading(true).build());

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mMusicNameView.setText(musicInfo.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FileUtil.formatFileSize(0, musicInfo.getSize()));
        stringBuilder.append(" ");
        stringBuilder.append(musicInfo.getArtist());
        stringBuilder.append("-");
        stringBuilder.append(musicInfo.getAlbumName());
        holder.mMusicArtistView.setText(stringBuilder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.playList(mSongIds, position, ListType.Song);
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