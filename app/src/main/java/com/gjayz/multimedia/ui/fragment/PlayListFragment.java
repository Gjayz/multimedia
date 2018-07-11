package com.gjayz.multimedia.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.MusicInfo;
import com.gjayz.multimedia.ui.adapter.MusicAdapter;

import java.util.List;

import butterknife.BindView;

public class PlayListFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<MusicInfo> mMusicList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_playlist;
    }

    @Override
    public void init() {
        mMusicList = MusicManager.getInstance(mContext).getMusicList();
        MusicAdapter musicAdapter = new MusicAdapter(mContext, mMusicList);
        musicAdapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int postion) {
//                MusicInfo musicInfo = mMusicList.get(postion);
//                playMusic(postion, musicInfo.getPath());
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(musicAdapter);
    }
}