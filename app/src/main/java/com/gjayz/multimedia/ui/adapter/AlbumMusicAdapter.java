package com.gjayz.multimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.utils.TimeUtils;

import java.util.List;

public class AlbumMusicAdapter extends BaseQuickAdapter<SongInfo, AlbumMusicAdapter.Holder> {

    public AlbumMusicAdapter(@Nullable List<SongInfo> data) {
        super(R.layout.item_album_music_layout, data);
    }

    @Override
    protected void convert(Holder helper, SongInfo item) {
        helper.mMusicIndexView.setText(String.valueOf(item.getTrack()));
        helper.mMusicNameView.setText(item.getTitle());
        helper.mMusicDurationView.setText(TimeUtils.formatMusicTime(item.getDuration() / 1000));
    }

    static class Holder extends BaseViewHolder {
        TextView mMusicIndexView;
        TextView mMusicNameView;
        TextView mMusicDurationView;

        public Holder(View itemView) {
            super(itemView);
            mMusicIndexView = itemView.findViewById(R.id.musci_track_index_tv);
            mMusicNameView = itemView.findViewById(R.id.music_title_tv);
            mMusicDurationView = itemView.findViewById(R.id.music_duration_tv);
        }
    }
}
