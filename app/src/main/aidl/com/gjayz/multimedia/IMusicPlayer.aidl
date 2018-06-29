// IMusicPlayer.aidl
package com.gjayz.multimedia;

import com.gjayz.multimedia.IMusicPlayCallBack;

interface IMusicPlayer {
    boolean isPlaying();
    void play(String path);
    void pause();
    void resume();
    void stop();
    void playNextMusic();
    void playPreMusic();
    void registerMusicCallBack(in IMusicPlayCallBack callback);
}