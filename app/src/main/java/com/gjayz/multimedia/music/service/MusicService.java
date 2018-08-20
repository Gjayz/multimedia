package com.gjayz.multimedia.music.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.gjayz.multimedia.BuildConfig;
import com.gjayz.multimedia.music.bean.MusicPlaybackTrack;
import com.gjayz.multimedia.music.data.MusicDbHelper;
import com.gjayz.multimedia.music.player.ListType;
import com.gjayz.multimedia.music.player.MediaPlayerControl;
import com.gjayz.multimedia.music.player.MusicPlaybackService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MusicService extends Service {

    private static final String TAG = "MusicService";

    public static final String PLAYSTATE_CHANGED = "com.gjayz.multimedia.playstatechanged";
    public static final String POSITION_CHANGED = "com.gjayz.multimedia.positionchanged";
    public static final String META_CHANGED = "com.gjayz.multimedia.metachanged";
    public static final String QUEUE_CHANGED = "com.gjayz.multimedia.queuechanged";
    public static final String PLAYLIST_CHANGED = "com.gjayz.multimedia.playlistchanged";
    public static final String REPEATMODE_CHANGED = "com.gjayz.multimedia.repeatmodechanged";
    public static final String SHUFFLEMODE_CHANGED = "com.gjayz.multimedia.shufflemodechanged";
    public static final String TRACK_ERROR = "com.gjayz.multimedia.trackerror";

    public static final String KEY_PLAY_STATUS = "key_play_status";
    private PlayStatus mStatus;

    public enum PlayStatus {
        UNKNOWN(0),
        PLAY(1),
        PAUSE(2),
        STOP(3);

        public int status;

        PlayStatus(int status) {
            this.status = status;
        }

        public static PlayStatus getTypeById(int status) {
            for (PlayStatus palyStatus : values()) {
                if (palyStatus.status == status) {
                    return palyStatus;
                }
            }

            throw new IllegalArgumentException("Unrecognized id: " + status);
        }
    }

    private MediaPlayerControl mMediaPlayerControl;
    private AudioManager mAudioManager;

    public int mCurPos;
    private int mNextPos;
    private String mFileToPlay;
    private int mOpenFailedCounter;
    public MusicDbHelper mMusicDbHelper;

    private ArrayList<MusicPlaybackTrack> mPlaylist = new ArrayList<MusicPlaybackTrack>(100);

    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    if (isPlaying()){
                        pauseInternal();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
            }
        }
    };

    private int mMediaMountedCount;
    private int mRepeatMode;
    private int mShuffleMode;
    private int mQueuePosition;
    private MusicMsgHandler mMusicMsgHandler;

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
        mMusicMsgHandler = new MusicMsgHandler(this);
        initMediaPlayer();
        createDbHelper();
    }

    private void initMediaPlayer() {
        mMediaPlayerControl = new MediaPlayerControl(this, mMusicMsgHandler);
    }

    private void createDbHelper() {
        mMusicDbHelper = new MusicDbHelper(getContentResolver());
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
            mStatus = PlayStatus.PLAY;

            notifyChange(META_CHANGED);
            notifyChange(PLAYSTATE_CHANGED);
        }
    }

    public void pauseInternal() {
        if (mMediaPlayerControl != null && mMediaPlayerControl.isPlaying()) {
            mMediaPlayerControl.pause();
            mStatus = PlayStatus.PAUSE;
            notifyChange(PLAYSTATE_CHANGED);
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

    public void setNextTrack() {
        setNextTrack(getNextPosition());
    }

    private void setNextTrack(int position) {
        mNextPos = position;
        if (BuildConfig.DEBUG) Log.d(TAG, "setNextTrack: next play position = " + mNextPos);
        if (mNextPos >= 0 && mPlaylist != null && mNextPos < mPlaylist.size()) {
            long id = mPlaylist.get(mNextPos).mId;
            mMediaPlayerControl.setNextDataSource(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + id);
        } else {
            mMediaPlayerControl.setNextDataSource(null);
        }
    }

    private int getNextPosition() {
        return mCurPos + 1;
    }

    private boolean isPlayingInternal() {
        return mMediaPlayerControl != null && mMediaPlayerControl.isPlaying();
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void abandonAudioFocus() {
        mAudioManager.abandonAudioFocus(null);
    }

    public boolean openFile(String path) {
        if (BuildConfig.DEBUG) Log.d(TAG, "openFile: path = " + path);
        synchronized (this) {
            if (TextUtils.isEmpty(path)) {
                return false;
            }

            Uri uri = Uri.parse(path);
            boolean shouldAddToPlaylist = true;
            long id = -1;
            try {
                id = Long.parseLong(uri.getLastPathSegment());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (id != -1 && path.startsWith(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString())) {
                mMusicDbHelper.updateCursor(uri);
            } else if (id != -1 && path.startsWith(MediaStore.Files.getContentUri("external").toString())) {
                mMusicDbHelper.updateCursor(id);
            } else {
                String where = MediaStore.Audio.Media.DATA + "=?";
                String[] selectionArgs = new String[]{path};
                mMusicDbHelper.updateCursor(where, selectionArgs);
            }

            try {
                if (mMusicDbHelper.isCursorOpen() && shouldAddToPlaylist) {
//                    mPlaylist.add(new MusicPlaybackTrack(mMusicDbHelper.getMusicId(), -1, ListType.PlayList, -1));
//                    mCurPos = 0;
                }
            } catch (final UnsupportedOperationException ex) {
                ex.printStackTrace();
            }

            mFileToPlay = path;
            mMediaPlayerControl.setDataSource(mFileToPlay);
            if (mMediaPlayerControl.isInitPlayer()) {
                mOpenFailedCounter = 0;
                return true;
            }

            stopInternal();
        }
        return false;
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

            mMusicDbHelper.updateCursor(mPlaylist.get(mCurPos).mId);
            while (true) {
                if (openFile(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/"
                        + mMusicDbHelper.getMusicId())) {
                    break;
                }

                mMusicDbHelper.closeCursor();

                if (openNext) {
                    setNextTrack();
                }
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
            mMusicDbHelper.closeCursor();
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

    public String getTrackName() {
        return mMusicDbHelper.getTrackName();
    }

    public void prev() {
    }

    public void next() {
    }

    public long duration() {
        return mMusicDbHelper.duration();
    }

    public long position() {
        return mCurPos;
    }

    public long seek(long pos) {
        return pos;
    }

    public String getAlbumName() {
        return mMusicDbHelper.getAlbumName();
    }

    public int getAlbumId() {
        return mMusicDbHelper.getAlbumId();
    }

    public String getArtistName() {
        return mMusicDbHelper.getArtistName();
    }

    public long getArtistId() {
        return mMusicDbHelper.getArtistId();
    }

    public void enqueue(long[] list, int action) {

    }

    public int getMediaMountedCount() {
        return mMediaMountedCount;
    }

    public int getRepeatMode() {
        return mRepeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
    }

    public int removeTracks(long id) {
        return 0;
    }

    public int removeTracks(int first, int last) {
        return 0;
    }

    public int getShuffleMode() {
        return mShuffleMode;
    }

    public void setShuffleMode(int shuffleMode) {
        mShuffleMode = shuffleMode;
    }

    public String getPath() {
        return mMusicDbHelper.getPath();
    }

    public void moveQueueItem(int from, int to) {

    }

    public void setQueuePosition(int queuePosition) {
        mQueuePosition = queuePosition;
    }

    public long[] getQueue() {
        if (mPlaylist != null){
            long[] songs = new long[mPlaylist.size()];
            for (int i = 0; i < mPlaylist.size(); i ++) {
                songs[i] = mPlaylist.get(i).mId;
            }
            return songs;
        }else {
            return null;
        }
    }

    public int getQueuePosition() {
        return mQueuePosition;
    }

    public boolean isPlaying() {
        return isPlayingInternal();
    }

    public void stop() {
        if (mMediaPlayerControl != null){
            mMediaPlayerControl.stop();
            mStatus = PlayStatus.STOP;

            notifyChange(PLAYSTATE_CHANGED);
        }
    }

    public void notifyChange(String action) {
        Intent intent = new Intent(action);
        switch (action) {
            case META_CHANGED:
                intent.putExtra(MediaStore.Audio.Media._ID, getAudioId());
                intent.putExtra(MediaStore.Audio.Media.TITLE, getTrackName());
                intent.putExtra(MediaStore.Audio.Media.ARTIST, getArtistName());
                intent.putExtra(MediaStore.Audio.Media.ARTIST_ID, getArtistId());
                intent.putExtra(MediaStore.Audio.Media.ALBUM, getAlbumName());
                intent.putExtra(MediaStore.Audio.Media.ALBUM_ID, getAlbumId());
                break;
            case PLAYSTATE_CHANGED:
                intent.putExtra(KEY_PLAY_STATUS, mStatus.status);
                break;
        }
        sendStickyBroadcast(intent);
    }

    public void setAndRecordPlayPos() {
        mCurPos = mNextPos;
    }

    @SuppressLint("HandlerLeak")
    public class MusicMsgHandler extends Handler {

        public static final int MSG_GOTO_NEXT_TRACK = 1;

        private WeakReference<MusicService> mServiceWeakReference;

        public MusicMsgHandler(MusicService musicService) {
            mServiceWeakReference = new WeakReference<>(musicService);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GOTO_NEXT_TRACK:
                    MusicService service = mServiceWeakReference.get();
                    service.setAndRecordPlayPos();
                    service.setNextTrack();
                    if (service.mMusicDbHelper != null) {
                        if (service.mMusicDbHelper.isCursorOpen()) {
                            service.mMusicDbHelper.closeCursor();
                        }
                        service.mMusicDbHelper.updateCursor(service.mPlaylist.get(service.mCurPos).mId);
                    }
                    service.notifyChange(MusicService.META_CHANGED);
                    break;
            }
        }
    }
}