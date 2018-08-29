package com.gjayz.multimedia.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.music.player.ListType;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.utils.FileUtil;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MusicAdapter extends BaseQuickAdapter<SongInfo, MusicAdapter.MusicHolder> {

    public MusicAdapter(List<SongInfo> musicInfos) {
        super(R.layout.item_music_layout, musicInfos);
    }

    @Override
    protected void convert(MusicHolder helper, SongInfo item) {
        try {
            ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(item.getAlbumId()).toString(),
                    helper.mMusicAlbumView, new DisplayImageOptions.Builder().cacheInMemory(true)
                            .showImageOnLoading(R.drawable.ic_empty_music2)
                            .resetViewBeforeLoading(true).build());

        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.mMusicNameView.setText(item.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FileUtil.formatFileSize(0, item.getSize()));
        stringBuilder.append(" ");
        stringBuilder.append(item.getArtist());
        stringBuilder.append("-");
        stringBuilder.append(item.getAlbumName());
        helper.mMusicArtistView.setText(stringBuilder);
    }

    class MusicHolder extends BaseViewHolder {

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition() - getHeaderLayoutCount();
                    MusicPlayer.playList(mData, position, ListType.Song);
                }
            });
        }
    }
}