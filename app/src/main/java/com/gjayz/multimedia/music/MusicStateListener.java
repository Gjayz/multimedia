package com.gjayz.multimedia.music;

/**
 * Listens for playback changes to send the the fragments bound to this activity
 */
public interface MusicStateListener {
    void restartLoader();

    void onPlaylistChanged();

    void onPlayStatusChanged(int status);

    void onMetaChanged(long id, String title, String artist, int albumId);
}