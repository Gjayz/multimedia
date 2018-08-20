package com.gjayz.multimedia.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.gjayz.multimedia.music.MusicStateListener;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.music.service.MusicService;
import com.gjayz.multimedia.net.base.BaseBean;
import com.gjayz.multimedia.net.base.IView;
import com.gjayz.multimedia.swipeback.SwipeBackHelper;
import com.gjayz.multimedia.swipeback.SwipeBackPage;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseActivity extends AppCompatActivity implements ServiceConnection,
        MusicStateListener, LifecycleProvider<ActivityEvent>, IView<BaseBean> {

    private MusicPlayer.ServiceToken mToken;
    private MusicStatsCallbackReceiver mStatsCallbackReceiver;
    protected SwipeBackPage mSwipeBackPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        mSwipeBackPage = SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
        setContentView(layoutId());
        ButterKnife.bind(this);
        initActivity();
        mToken = MusicPlayer.bindToService(this, this);
        registerReceiver();
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
        SwipeBackHelper.onDestroy(this);
        MusicPlayer.unbindFromService(mToken);
        unRegisterReceiver();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private void registerReceiver() {
        mStatsCallbackReceiver = new MusicStatsCallbackReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.PLAYSTATE_CHANGED);
        intentFilter.addAction(MusicService.POSITION_CHANGED);
        intentFilter.addAction(MusicService.META_CHANGED);
        intentFilter.addAction(MusicService.QUEUE_CHANGED);
        intentFilter.addAction(MusicService.PLAYLIST_CHANGED);
        intentFilter.addAction(MusicService.REPEATMODE_CHANGED);
        intentFilter.addAction(MusicService.SHUFFLEMODE_CHANGED);
        intentFilter.addAction(MusicService.TRACK_ERROR);
        registerReceiver(mStatsCallbackReceiver, intentFilter);
    }

    private void unRegisterReceiver() {
        if (mStatsCallbackReceiver != null) {
            unregisterReceiver(mStatsCallbackReceiver);
        }
    }

    public static class MusicStatsCallbackReceiver extends BroadcastReceiver {

        private WeakReference<MusicStateListener> mWeakReference;

        public MusicStatsCallbackReceiver(MusicStateListener musicStateListener) {
            super();
            mWeakReference = new WeakReference<>(musicStateListener);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                MusicStateListener musicStateListener = mWeakReference.get();
                switch (action) {
                    case MusicService.PLAYSTATE_CHANGED:
                        int playStatus = intent.getIntExtra(MusicService.KEY_PLAY_STATUS, -1);
                        musicStateListener.onPlayStatusChanged(playStatus);
                        break;
                    case MusicService.POSITION_CHANGED:
                        break;
                    case MusicService.META_CHANGED:
                        long id = intent.getLongExtra(MediaStore.Audio.Media._ID, -1);
                        String title = intent.getStringExtra(MediaStore.Audio.Media.TITLE);
                        String aritstName = intent.getStringExtra(MediaStore.Audio.Media.ARTIST);
                        int albumId = intent.getIntExtra(MediaStore.Audio.Media.ALBUM_ID, -1);
                        musicStateListener.onMetaChanged(id, title, aritstName, albumId);
                        break;
                    case MusicService.QUEUE_CHANGED:
                        break;
                    case MusicService.PLAYLIST_CHANGED:
                        musicStateListener.onPlaylistChanged();
                        break;
                    case MusicService.REPEATMODE_CHANGED:
                        break;
                    case MusicService.SHUFFLEMODE_CHANGED:
                        break;
                    case MusicService.TRACK_ERROR:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void restartLoader() {

    }

    @Override
    public void onPlaylistChanged() {

    }

    @Override
    public void onMetaChanged(long id, String title, String artist, int albumId) {

    }

    @Override
    public void onPlayStatusChanged(int status) {

    }

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    public void showInfo(String msg) {

    }

    @Override
    public void showInfo(int msg) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showData(BaseBean baseBean) {

    }

    @Override
    public void showError(int errCode, String errMsg) {
    }
}