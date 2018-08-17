package com.gjayz.multimedia.music.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MusicDbHelper {

    public static final int COLUM_ID = 0;
    public static final int COLUM_ARTIST = 1;
    public static final int COLUM_ALBUM = 2;
    public static final int COLUM_TITLE = 3;
    public static final int COLUM_DATA = 4;
    public static final int COLUM_MIME_TYPE = 5;
    public static final int COLUM_ALBUM_ID = 6;
    public static final int COLUM_ARTIST_ID = 7;

    private final Object mLock = new Object();

    public static final String[] PROJECTION = new String[]{
            MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID
    };

    public static final String[] ALBUM_PROJECTION = new String[]{
            MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.LAST_YEAR
    };

    private ContentResolver mContentResolver;
    private Cursor mCursor;
    private Cursor mAlbumCursor;

    public MusicDbHelper(ContentResolver con) {
        mContentResolver = con;
    }

    public void updateCursor(long id) {
        updateCursor(MediaStore.Audio.Media._ID + "=" + id, null);
    }

    public void updateCursor(Uri uri) {
        synchronized (mLock) {
            closeCursor();
            mCursor = openCursorAndGoToFirst(uri, PROJECTION, null, null);
        }
        updateAlbumCursor();
    }


    public void updateCursor(String selection, String[] selectionArags) {
        synchronized (this) {
            closeCursor();
            mCursor = openCloseAndMoveToFirst(PROJECTION, selection, selectionArags);
        }
    }

    private void updateAlbumCursor() {
        long albumId = getAlbumId();
        if (albumId >= 0) {
            mAlbumCursor = openCursorAndGoToFirst(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    ALBUM_PROJECTION, "_id=" + albumId, null);
        } else {
            mAlbumCursor = null;
        }
    }

    public void updateAlbumCursor(String selection, String[] selectionArags) {
        synchronized (this) {
            closeCursor();
            mAlbumCursor = openCloseAndMoveToFirst(ALBUM_PROJECTION, selection, selectionArags);
        }
    }

    private Cursor openCloseAndMoveToFirst(String[] projects, String selection, String[] selectionArgs) {
        Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projects, selection, selectionArgs, null);
        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                cursor.close();
                cursor = null;
            }
        }
        return cursor;
    }

    private Cursor openCursorAndGoToFirst(Uri uri, String[] projection,
                                          String selection, String[] selectionArgs) {
        Cursor c = mContentResolver.query(uri, projection, selection, selectionArgs, null);
        if (c == null) {
            return null;
        }
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }

    public void closeCursor() {
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }

        if (mAlbumCursor != null) {
            mAlbumCursor.close();
            mAlbumCursor = null;
        }
    }

    public long getMusicId() {
        return mCursor.getLong(COLUM_ID);
    }

    public int getAlbumId() {
        return mCursor.getInt(COLUM_ALBUM_ID);
    }

    public boolean isCursorOpen() {
        return mCursor != null && !mCursor.isClosed();
    }

    public String getAlbumName() {
        return mCursor.getString(COLUM_ALBUM);
    }

    public String getArtistName() {
        return mCursor.getString(COLUM_ARTIST);
    }

    public long getArtistId() {
        return mCursor.getLong(COLUM_ARTIST_ID);
    }

    public long duration() {
        return 0;
    }

    public String getTrackName() {
        return mCursor.getString(COLUM_TITLE);
    }

    public String getPath() {
        return mCursor.getString(COLUM_DATA);
    }
}