package com.gjayz.multimedia.ui.activity;

import android.support.v4.view.ViewPager;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.ui.adapter.MusicPlayAdapter;

import butterknife.BindView;

public class MusicPlayActivity extends BaseActivity {

    private static final String TAG = "MusicPlayActivity";
    @BindView(R.id.music_play_viewpager)
    ViewPager mViewPager;

    private long[] mPlayList;

    @Override
    public int layoutId() {
        return R.layout.activity_music_play;
    }

    @Override
    public void initActivity() {
        mSwipeBackPage.setSwipeBackEnable(false);
        mPlayList = MusicPlayer.getPlayList();

        mViewPager.setOffscreenPageLimit(3);
        MusicPlayAdapter musicPlayAdapter = new MusicPlayAdapter(getSupportFragmentManager(), mPlayList);
        mViewPager.setAdapter(musicPlayAdapter);
    }
}