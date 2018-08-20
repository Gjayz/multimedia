package com.gjayz.multimedia.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.ui.adapter.MusicAdapter;

import java.util.List;

import butterknife.BindView;

public class PlayListFragment extends com.gjayz.multimedia.ui.fragment.v4.BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<SongInfo> mMusicList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_playlist;
    }

    @Override
    public void init() {
        mMusicList = MusicManager.getInstance(mContext).getMusicList();
        MusicAdapter musicAdapter = new MusicAdapter(mContext, mMusicList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(musicAdapter);
    }
}