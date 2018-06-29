package com.gjayz.multimedia.music.bean;

public class MusicInfo {

    int id;
    String title;
    String displayName;
    String albumName;
    int albumId;
    String artist;
    long size;
    int duration;
    String path;

    public MusicInfo() {
    }

    public MusicInfo(int id, String title, String displayName, String albumName,
                     int albumId, String artist, long size, int duration, String path) {
        this.id = id;
        this.title = title;
        this.albumName = albumName;
        this.albumId = albumId;
        this.artist = artist;
        this.size = size;
        this.duration = duration;
        this.path = path;
        this.displayName = displayName;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbumName() {
        return albumName == null ? "" : albumName;
    }

    public void setAlbumName(String album) {
        this.albumName = album;
    }

    public String getArtist() {
        return artist == null ? "" : artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getDisplayName() {
        return displayName == null ? "" : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}