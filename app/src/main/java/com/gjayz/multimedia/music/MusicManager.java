package com.gjayz.multimedia.music;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.music.bean.ArtistInfo;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class MusicManager {

    private static volatile MusicManager sMusicManager;
    private Context mContext;

    private static final String[] PROJECT_ARTIST = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_KEY,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
    };

    private static final String[] PROJECT_ALBUM = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID
    };

    private List<Album> mAlbumList;

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
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    if (!FileUtil.isExist(filePath)) {
                        continue;
                    }

                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                    SongInfo musicInfo = new SongInfo(id, title, displayName, album, albumId, artist, size, duration, filePath);
                    musicInfos.add(musicInfo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return musicInfos;
    }

    public List<ArtistInfo> getArtistList() {
        ArrayList<ArtistInfo> artistInfos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, PROJECT_ARTIST, null, null,
                    MediaStore.Audio.Artists.ARTIST);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.ARTIST));
                    int num_tracks = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
                    int num_album = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
                    List<Integer> artistAlbumList = getArtistAlbumList(id);
                    artistInfos.add(new ArtistInfo(id, artist, num_tracks, num_album, artistAlbumList));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return artistInfos;
    }

    public List<Integer> getArtistAlbumList(int artist_id) {
        ArrayList<Integer> albumsList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Audio.Media.ARTIST_ID + "=?",
                    new String[]{String.valueOf(artist_id)}, MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    albumsList.add(id);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return albumsList;
    }

    public List<Album> getAlbumList() {
        ArrayList<Album> albumInfos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, PROJECT_ALBUM, null, null,
                    MediaStore.Audio.Albums.ALBUM);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM));
                    int artist_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ARTIST));
                    albumInfos.add(new Album(id, album, artist_id, artist));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return albumInfos;
    }
}
