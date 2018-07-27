package com.gjayz.multimedia.ui.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.swipeback.SwipeBackHelper;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements ServiceConnection {

    private MusicPlayer.ServiceToken mToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
        setContentView(layoutId());
        ButterKnife.bind(this);
        initActivity();
        mToken = MusicPlayer.bindToService(this, this);
    }

    public abstract int layoutId();

    public abstract void initActivity();

    @Override
    protected void onResume() {
        super.onResume();
        if (MusicPlayer.sService == null) {
            mToken = MusicPlayer.bindToService(this, this);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}