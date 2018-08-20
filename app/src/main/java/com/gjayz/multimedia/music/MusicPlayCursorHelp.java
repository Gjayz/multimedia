package com.gjayz.multimedia.music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.gjayz.multimedia.music.bean.SongInfo;

public class MusicPlayCursorHelp {

    private static String[] PROJECTS = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
    };

    public static synchronized SongInfo getSongInfo(Context context, long id) {
        SongInfo songInfo = null;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cusor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media._ID + "=?", new String[]{String.valueOf(id)}, null);
        if (cusor != null) {
            if (cusor.moveToFirst()) {
                int _id = cusor.getInt(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String title = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String displayName = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                String album = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                int albumId = cusor.getInt(cusor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String artist = cusor.getString(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                long size = cusor.getLong(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                int duration = cusor.getInt(cusor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                songInfo = new SongInfo(id, title, displayName, album, albumId, artist, size, duration, "");
            }

            cusor.close();
            cusor = null;
        }
        return songInfo;
    }
}
