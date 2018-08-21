package com.gjayz.multimedia.ui.activity;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.ui.adapter.MusicPlayAdapter;

import butterknife.BindView;

public class MusicPlayActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MusicPlayActivity";
    @BindView(R.id.music_play_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.operate_layout)
    View mOperateLayout;
    private long[] mPlayList;
    private Handler mHandler = new Handler();
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
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged: state = " + state);
        if (state == ViewPager.SCROLL_STATE_IDLE){
            mOperateLayout.setVisibility(View.VISIBLE);
        }else if (state == ViewPager.SCROLL_STATE_DRAGGING){
            mOperateLayout.setVisibility(View.GONE);
        }else {
            mOperateLayout.setVisibility(View.GONE);
        }
    }
}