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
        return mMusicService.get().getQueuePosition();
    }

    @Override
    public boolean isPlaying() throws RemoteException {
        return mMusicService.get().isPlaying();
    }

    @Override
    public void stop() throws RemoteException {

        mMusicService.get().stop();
    }

    @Override
    public void pause() throws RemoteException {
        mMusicService.get().pauseInternal();
    }

    @Override
    public void play() throws RemoteException {
        mMusicService.get().playInternal();
    }

    @Override
    public void playPosition(int position) throws RemoteException {
        mMusicService.get().goToPosition(position);
    }

    @Override
    public void prev() throws RemoteException {
        mMusicService.get().prev();
    }

    @Override
    public void next() throws RemoteException {
        mMusicService.get().next();
    }

    @Override
    public long duration() throws RemoteException {
        return mMusicService.get().duration();
    }

    @Override
    public long position() throws RemoteException {
        return mMusicService.get().position();
    }

    @Override
    public long seek(long pos) throws RemoteException {
        return mMusicService.get().seek(pos);
    }

    @Override
    public String getTrackName() throws RemoteException {
        return mMusicService.get().getTrackName();
    }

    @Override
    public String getAlbumName() throws RemoteException {
        return  mMusicService.get().getAlbumName();
    }

    @Override
    public int getAlbumId() throws RemoteException {
        return  mMusicService.get().getAlbumId();
    }

    @Override
    public String getArtistName() throws RemoteException {
        return  mMusicService.get().getArtistName();
    }

    @Override
    public int getArtistId() throws RemoteException {
        return  mMusicService.get().getArtistId();
    }

    @Override
    public void enqueue(long[] list, int action) throws RemoteException {
        mMusicService.get().enqueue(list, action);
    }

    @Override
    public long[] getQueue() throws RemoteException {
        return  mMusicService.get().getQueue();
    }

    @Override
    public void moveQueueItem(int from, int to) throws RemoteException {
        mMusicService.get().moveQueueItem(from, to);
    }

    @Override
    public void setQueuePosition(int index) throws RemoteException {
        mMusicService.get().setQueuePosition(index);
    }

    @Override
    public String getPath() throws RemoteException {
        return mMusicService.get().getPath();
    }

    @Override
    public long getAudioId() throws RemoteException {
        return mMusicService.get().getAudioId();
    }

    @Override
    public void setShuffleMode(int shufflemode) throws RemoteException {
        mMusicService.get().setShuffleMode(shufflemode);
    }

    @Override
    public int getShuffleMode() throws RemoteException {
        return mMusicService.get().getShuffleMode();
    }

    @Override
    public int removeTracks(int first, int last) throws RemoteException {
        return mMusicService.get().removeTracks(first, last);
    }

    @Override
    public int removeTrack(long id) throws RemoteException {
        return mMusicService.get().removeTracks(id);
    }

    @Override
    public void setRepeatMode(int repeatmode) throws RemoteException {
        mMusicService.get().setRepeatMode(repeatmode);
    }

    @Override
    public int getRepeatMode() throws RemoteException {
        return mMusicService.get().getRepeatMode();
    }

    @Override
    public int getMediaMountedCount() throws RemoteException {
        return mMusicService.get().getMediaMountedCount();
    }

    @Override
    public int getAudioSessionId() throws RemoteException {
        return mMusicService.get().getAudioSessionId();
    }
}