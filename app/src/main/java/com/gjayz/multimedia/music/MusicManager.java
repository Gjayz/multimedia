package com.gjayz.multimedia.music;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.music.bean.ArtistInfo;
import com.gjayz.multimedia.music.bean.School;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.utils.FileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.validation.Validator;

public class MusicManager {

    private static volatile MusicManager sMusicManager;
    private Context mContext;

    private static final String[] PROJECT_SONG = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
    };

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


    private static final String[] PROJECT_ALBUM2 = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID
    };

    private static final String[] PROJECT_SCHOOL = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.GenresColumns.NAME
    };

    private static final String[] PROJECT_SCHOOL_AUDIO = {
            MediaStore.Audio.Genres.Members.AUDIO_ID,
            MediaStore.Audio.Genres.Members.GENRE_ID
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
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                    MediaStore.Audio.Media.IS_MUSIC + "=?", new String[]{String.valueOf(1)},
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
                    int track = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                    SongInfo musicInfo = new SongInfo(id, title, displayName, album, albumId, artist, track, size, duration, filePath);
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
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, PROJECT_ARTIST,
                    null, null, MediaStore.Audio.Artists.ARTIST);
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
                    null, MediaStore.Audio.Media.ARTIST_ID + "=?", new String[]{String.valueOf(artist_id)},
                    MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
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
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, PROJECT_ALBUM, null,
                    null, MediaStore.Audio.Albums.ALBUM);
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


    public List<Album> getAlbumList(int artistId) {
        ArrayList<Album> albumInfos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, PROJECT_ALBUM,
                    MediaStore.Audio.Media.ARTIST_ID + "=?", new String[]{String.valueOf(artistId)}, MediaStore.Audio.Albums.ALBUM);
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

    public List<School> getSchoolList() {
        ArrayList<School> schoolInfoList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, PROJECT_SCHOOL,
                    null, null, MediaStore.Audio.GenresColumns.NAME);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.GenresColumns.NAME));
                    schoolInfoList.add(new School(id, name, getSchoolAudioList(id)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return schoolInfoList;
    }

    public List<SongInfo> getSchoolAudioList(int school_id) {
        ArrayList<SongInfo> songInfosList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Genres.Members.getContentUri("external", school_id), PROJECT_SCHOOL_AUDIO,
                    MediaStore.Audio.Genres.Members.GENRE_ID + "=?", new String[]{String.valueOf(school_id)}, MediaStore.Audio.Genres.Members.AUDIO_ID);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    long audio_id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.Members.AUDIO_ID));
                    songInfosList.add(getSong(audio_id));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return songInfosList;
    }

    public SongInfo getSong(long songId) {
        SongInfo songInfo = null;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, PROJECT_SONG,
                    MediaStore.Audio.Media._ID + "=?", new String[]{String.valueOf(songId)}, null);
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
                    int track = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                    songInfo = new SongInfo(id, title, displayName, album, albumId, artist, track, size, duration, filePath);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return songInfo;
    }

    public List<SongInfo> getAlbumMusicList(int albumId) {
        List<SongInfo> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, PROJECT_SONG,
                    MediaStore.Audio.Media.ALBUM_ID + "=?", new String[]{String.valueOf(albumId)}, null);
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
                    int albumId2 = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    int track = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                    result.add(new SongInfo(id, title, displayName, album, albumId, artist, track, size, duration, filePath));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public List<Album> getSchoolAlbum(int school_id) {
        Set<Album> albumSet = new HashSet<>();
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Genres.Members.getContentUri("external", school_id), PROJECT_SCHOOL_AUDIO,
                    MediaStore.Audio.Genres.Members.GENRE_ID + "=?", new String[]{String.valueOf(school_id)}, MediaStore.Audio.Genres.Members.AUDIO_ID);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    long audio_id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.Members.AUDIO_ID));
                    albumSet.add(getAlbum(audio_id));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        List<Album> result = new ArrayList<>();
        result.addAll(albumSet);
        return result;
    }

    private Album getAlbum(long audio_id) {
        Album album = null;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, PROJECT_ALBUM2,
                    MediaStore.Audio.Media._ID + "=?", new String[]{String.valueOf(audio_id)}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    int albumId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                    String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM));
                    int artist_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ARTIST));
                    album = new Album(albumId, albumName, artist_id, artist);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return album;
    }
}