package com.gjayz.multimedia.music.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class MediaStoreHelper {

    public static final String[] ALL_SONG_SELECTION = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TRACK
    };

    public static Cursor getPlayListCursor(Context context, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, sortOrder);
    }

    public static Cursor getAllSongsCursor(Context context, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        String selection = MediaStore.Audio.Media.IS_MUSIC + "= 1";
        return contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ALL_SONG_SELECTION, selection, null, sortOrder);
    }

    public static Cursor getAlbumCursor(Context context, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, sortOrder);
    }


    public static Cursor getArtistCursor(Context context, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, sortOrder);
    }

    public static Cursor getSchoolCursor(Context context, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, null, null, null, sortOrder);
    }
}