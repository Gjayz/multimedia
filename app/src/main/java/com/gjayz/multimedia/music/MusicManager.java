package com.gjayz.multimedia.music;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class MusicManager {

    private static volatile MusicManager sMusicManager;
    private Context mContext;

    private MusicManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static MusicManager getInstance(Context context) {
        if (sMusicManager == null) {
            synchronized (MusicManager.class) {
                if (sMusicManager == null) {
                    sMusicManager = new MusicManager(context.getApplicationContext());
                }
            }
        }
        return sMusicManager;
    }

    public List<SongInfo> getMusicList() {
        ArrayList<SongInfo> musicInfos = new ArrayList<>();
        Cursor cusor = null;
        try {
            cusor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (cusor != null) {
                while (cusor.moveToNext()) {
                    String filePath = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    if (!FileUtil.isExist(filePath)) {
                        continue;
                    }

                    long id = cusor.getLong(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String title = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String displayName = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                    String album = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    int albumId = cusor.getInt(cusor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String artist = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    long size = cusor.getLong(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    int duration = cusor.getInt(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                    SongInfo musicInfo = new SongInfo(id, title, displayName, album, albumId, artist, size, duration, filePath);
                    musicInfos.add(musicInfo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cusor != null) {
                cusor.close();
            }
        }
        return musicInfos;
    }
}