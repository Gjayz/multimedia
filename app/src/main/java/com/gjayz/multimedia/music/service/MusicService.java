package com.gjayz.multimedia.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.AudioEffect;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.gjayz.multimedia.BuildConfig;
import com.gjayz.multimedia.music.bean.MusicPlaybackTrack;
import com.gjayz.multimedia.music.player.ListType;
import com.gjayz.multimedia.music.player.MediaPlayerControl;
import com.gjayz.multimedia.music.player.MusicPlaybackService;

import java.util.ArrayList;

public class MusicService extends Service {

    private static final String TAG = "MusicService";

    private MediaPlayerControl mMediaPlayerControl;
    private AudioManager mAudioManager;

    private int mCurPos;
    private int mNextPos;

    private ArrayList<MusicPlaybackTrack> mPlaylist = new ArrayList<MusicPlaybackTrack>(100);

    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
            }
        }
    };

    public int getAudioSessionId() {
        int audioSenssionId;
        synchronized (this) {
            audioSenssionId = mMediaPlayerControl.getAudioSessionId();
        }
        return audioSenssionId;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicPlaybackService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mMediaPlayerControl = new MediaPlayerControl(this);
    }

    public void playInternal() {
        playInternal(true);
    }

    private void playInternal(boolean palyWithNextSong) {
        int status = mAudioManager.requestAudioFocus(mAudioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Starting playback: audio focus request status = " + status);

        if (status != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            return;
        }

        final Intent intent = new Intent(AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION);
        intent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, getAudioSessionId());
        intent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, getPackageName());
        sendBroadcast(intent);

        if (palyWithNextSong) {
            setNextTrack();
        } else {
            setNextTrack(mNextPos);
        }

        if (mMediaPlayerControl.isInitPlayer()) {
            mMediaPlayerControl.start();
        }
    }


    private void pauseInternal() {
        if (mMediaPlayerControl != null && mMediaPlayerControl.isPlaying()) {
            mMediaPlayerControl.pause();
        }
    }

    private void resumeInternal() {
        if (mMediaPlayerControl != null && !mMediaPlayerControl.isPlaying()) {
            mMediaPlayerControl.start();
        }
    }

    private void stopInternal() {

    }

    private void playNextMusicInternal() {

    }

    private void playPreMusicInternal() {

    }

    private void setNextTrack() {
        setNextTrack(getNextPosition(false));
    }

    private void setNextTrack(int position) {
        mNextPos = position;
        if (BuildConfig.DEBUG) Log.d(TAG, "setNextTrack: next play position = " + mNextPos);
        if (mNextPos >= 0 && mPlaylist != null && mNextPos < mPlaylist.size()) {
            final long id = mPlaylist.get(mNextPos).mId;
            mMediaPlayerControl.setNextDataSource(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + id);
        } else {
            mMediaPlayerControl.setNextDataSource(null);
        }
    }

    private int getNextPosition(boolean b) {
        return mCurPos + 1;
    }

    private boolean isPlayingInternal() {
        return mMediaPlayerControl != null && mMediaPlayerControl.isPlaying();
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void abandonAudioFocus() {
        mAudioManager.abandonAudioFocus(null);
    }

    public void openFile(String path) {

    }

    /**
     * 切换播放列表, 并播放指定歌曲
     *
     * @param list     播放列表的歌曲id
     * @param position 设置当前的播放id
     */
    public void open(long[] list, int position, ListType listType, long sourceId) {
        synchronized (this) {
            if (list != null) {
                long audioId = getAudioId();
                int length = list.length;
                boolean isNewList = true;
                if (mPlaylist.size() == length) {
                    isNewList = false;
                    for (int i = 0; i < length; i++) {
                        if (mPlaylist.get(i).mId != list[i]) {
                            isNewList = true;
                            break;
                        }
                    }
                }

                if (isNewList) {
                    addToPlayQueue(list, -1, listType, sourceId);
                }

                if (position >= 0) {
                    mCurPos = position;
                } else {

                }

                playCurrentAndNext();
            }
        }
    }

    private void playCurrentAndNext() {
        playCurrentAndMayNext(true);
    }

    private void playCurrentAndMayNext(boolean openNext) {
        synchronized (this) {
            if (mPlaylist.size() == 0) {
                return;
            }
        }
    }

    private void addToPlayQueue(long[] list, int position, ListType listType, long sourceId) {
        if (position < 0) {
            mPlaylist.clear();
            position = 0;
        }

        int length = list.length;
        mPlaylist.ensureCapacity(length);

        if (position > mPlaylist.size()) {
            position = mPlaylist.size();
        }

        ArrayList<MusicPlaybackTrack> addMusicList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            addMusicList.add(new MusicPlaybackTrack(list[i], sourceId, listType, position));
        }

        mPlaylist.addAll(position, addMusicList);

        if (mPlaylist.size() == 0) {

        }
    }

    /**
     * 获取当前正在播放的歌曲的id
     *
     * @return
     */
    public long getAudioId() {
        MusicPlaybackTrack track = getCurrentTrack();
        if (track != null) {
            return track.mId;
        }
        return -1;
    }

    /**
     * 当前正在播放的歌曲的对象
     *
     * @return
     */
    public MusicPlaybackTrack getCurrentTrack() {
        return getTrack(mCurPos);
    }

    public MusicPlaybackTrack getTrack(int position) {
        if (position >= 0 && position < mPlaylist.size() && mMediaPlayerControl.isInitPlayer()) {
            return mPlaylist.get(position);
        }
        return null;
    }
}