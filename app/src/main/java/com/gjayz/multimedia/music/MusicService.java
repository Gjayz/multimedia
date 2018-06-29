package com.gjayz.multimedia.music;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

import com.gjayz.multimedia.IMusicPlayCallBack;
import com.gjayz.multimedia.IMusicPlayer;
import com.gjayz.multimedia.music.bean.MusicInfo;

import java.io.IOException;
import java.util.List;

public class MusicService extends Service {

    private MediaPlayer mMediaPlayer;
    private List<MusicInfo> mMusicInfos;
    private AudioManager mAudioManager;
    private boolean mPausedByTransientLossOfFocus;

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicPlayer();
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

    private void initMediaPlayer(){
        mMediaPlayer = new MediaPlayer();
    }

    private void playInternal(String musicPath){
        try {
            requestAudioFocus();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(musicPath);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            abandonAudioFocus();
            e.printStackTrace();
        }
    }

    private void pauseInternal() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
    }
    private void resumeInternal() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
    }

    private void stopInternal() {

    }

    private void playNextMusicInternal() {

    }

    private void playPreMusicInternal() {

    }

    private boolean isPlayingInternal(){
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    private void requestAudioFocus(){
        mAudioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange){
                    case AudioManager.AUDIOFOCUS_LOSS:
                        if (isPlayingInternal()){
                            abandonAudioFocus();
                            pauseInternal();
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        if(isPlayingInternal()){
                            mPausedByTransientLossOfFocus = true;
                            pauseInternal();
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN:

                        //重新获得焦点，且符合播放条件，开始播放
                        if(!isPlayingInternal() && mPausedByTransientLossOfFocus){
                            mPausedByTransientLossOfFocus = false;
                            resumeInternal();
                        }
                        break;
                }
            }
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    private void abandonAudioFocus(){
        mAudioManager.abandonAudioFocus(null);
    }

    private class MusicPlayer extends IMusicPlayer.Stub {

        @Override
        public boolean isPlaying() throws RemoteException {
            return isPlayingInternal();
        }

        @Override
        public void play(String path) {
            playInternal(path);
        }

        @Override
        public void pause() {
            pauseInternal();
        }

        @Override
        public void resume() {
            resumeInternal();
        }

        @Override
        public void stop() {
            stopInternal();
        }

        @Override
        public void playNextMusic() {
            playNextMusicInternal();
        }

        @Override
        public void playPreMusic() {
            playPreMusicInternal();
        }

        @Override
        public void registerMusicCallBack(IMusicPlayCallBack callback) throws RemoteException {

        }
    }
}