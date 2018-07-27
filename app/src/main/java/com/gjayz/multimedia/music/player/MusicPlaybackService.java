package com.gjayz.multimedia.music.player;

import android.os.RemoteException;

import com.gjayz.multimedia.IMediaPlaybackService;
import com.gjayz.multimedia.music.service.MusicService;

import java.lang.ref.WeakReference;

public class MusicPlaybackService extends IMediaPlaybackService.Stub {

    private WeakReference<MusicService> mMusicService;

    public MusicPlaybackService(MusicService musicService) {
        mMusicService = new WeakReference<>(musicService);
    }

    @Override
    public void openFile(String path) throws RemoteException {
        mMusicService.get().openFile(path);
    }

    @Override
    public void open(long[] list, int position, int listType, long souceId) throws RemoteException {
        mMusicService.get().open(list, position, ListType.getTypeById(listType), souceId);
    }

    @Override
    public int getQueuePosition() throws RemoteException {
        return 0;
    }

    @Override
    public boolean isPlaying() throws RemoteException {
        return false;
    }

    @Override
    public void stop() throws RemoteException {

    }

    @Override
    public void pause() throws RemoteException {

    }

    @Override
    public void play() throws RemoteException {
        mMusicService.get().playInternal();
    }

    @Override
    public void prev() throws RemoteException {

    }

    @Override
    public void next() throws RemoteException {

    }

    @Override
    public long duration() throws RemoteException {
        return 0;
    }

    @Override
    public long position() throws RemoteException {
        return 0;
    }

    @Override
    public long seek(long pos) throws RemoteException {
        return 0;
    }

    @Override
    public String getTrackName() throws RemoteException {
        return null;
    }

    @Override
    public String getAlbumName() throws RemoteException {
        return null;
    }

    @Override
    public long getAlbumId() throws RemoteException {
        return 0;
    }

    @Override
    public String getArtistName() throws RemoteException {
        return null;
    }

    @Override
    public long getArtistId() throws RemoteException {
        return 0;
    }

    @Override
    public void enqueue(long[] list, int action) throws RemoteException {

    }

    @Override
    public long[] getQueue() throws RemoteException {
        return new long[0];
    }

    @Override
    public void moveQueueItem(int from, int to) throws RemoteException {

    }

    @Override
    public void setQueuePosition(int index) throws RemoteException {

    }

    @Override
    public String getPath() throws RemoteException {
        return null;
    }

    @Override
    public long getAudioId() throws RemoteException {
        return mMusicService.get().getAudioId();
    }

    @Override
    public void setShuffleMode(int shufflemode) throws RemoteException {

    }

    @Override
    public int getShuffleMode() throws RemoteException {
        return 0;
    }

    @Override
    public int removeTracks(int first, int last) throws RemoteException {
        return 0;
    }

    @Override
    public int removeTrack(long id) throws RemoteException {
        return 0;
    }

    @Override
    public void setRepeatMode(int repeatmode) throws RemoteException {

    }

    @Override
    public int getRepeatMode() throws RemoteException {
        return 0;
    }

    @Override
    public int getMediaMountedCount() throws RemoteException {
        return 0;
    }

    @Override
    public int getAudioSessionId() throws RemoteException {
        return mMusicService.get().getAudioSessionId();
    }
}