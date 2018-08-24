package com.gjayz.multimedia.music.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.gjayz.multimedia.music.service.MusicService;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 控制mediaplayer播放控制
 */
public class MediaPlayerControl implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private MediaPlayer mNextMediaPlayer;
    private WeakReference<Context> mContextWeakReference;
    private WeakReference<MusicService.MusicMsgHandler> msgHandlerWeakReference;
    private boolean isInitPlayer;
    private String mNextPlayPath;

    public MediaPlayerControl(Context context, MusicService.MusicMsgHandler musicMsgHandler) {
        mContextWeakReference = new WeakReference<>(context);
        msgHandlerWeakReference = new WeakReference<>(musicMsgHandler);
    }

    public void setDataSource(String path) {
        isInitPlayer = setDataSourceInternal(mMediaPlayer, path);
        if (isInitPlayer) {
            setNextDataSource(null);
        }
    }

    private boolean setDataSourceInternal(MediaPlayer mediaPlayer, String path) {
        if (!TextUtils.isEmpty(path)) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setOnCompletionListener(null);
                if (path.startsWith("content://")) {
                    mediaPlayer.setDataSource(mContextWeakReference.get(), Uri.parse(path));
                } else {
                    mediaPlayer.setDataSource(path);
                }
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置下一曲播放
     *
     * @param path
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setNextDataSource(String path) {
        if (!TextUtils.isEmpty(path)) {
            mNextPlayPath = null;
            try {
                mMediaPlayer.setNextMediaPlayer(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (mNextMediaPlayer != null) {
                mNextMediaPlayer.release();
                mNextMediaPlayer = null;
            }

            MediaPlayer mediaPlayer = new MediaPlayer();
            if (setDataSourceInternal(mediaPlayer, path)) {
                mNextMediaPlayer = mediaPlayer;
                mMediaPlayer.setNextMediaPlayer(mNextMediaPlayer);
                mNextPlayPath = path;
            } else {
                mediaPlayer.release();
                mMediaPlayer = null;
            }
        }
    }

    public boolean isInitPlayer() {
        return isInitPlayer;
    }

    public long seekTo(long time) {
        mMediaPlayer.seekTo((int) time);
        return time;
    }

    public void start() {
        mMediaPlayer.start();
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public void stop() {
        mMediaPlayer.stop();
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }

        if (mNextMediaPlayer != null) {
            mNextMediaPlayer.release();
        }
    }

    public void reset() {
        mMediaPlayer.reset();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp == mMediaPlayer && mNextMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = mNextMediaPlayer;
            mNextMediaPlayer = null;
            mNextPlayPath = null;
            msgHandlerWeakReference.get().sendEmptyMessage(MusicService.MusicMsgHandler.MSG_GOTO_NEXT_TRACK);
        } else {
            //当前播放列表播放结束
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public int getAudioSessionId() {
        return mMediaPlayer.getAudioSessionId();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public long position() {
        return mMediaPlayer.getCurrentPosition();
    }

    public long duration() {
        return mMediaPlayer.getDuration();
    }
}