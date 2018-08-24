package com.gjayz.multimedia.music.bean;

public class SongInfo {

    long id;
    String title;
    String displayName;
    String albumName;
    int albumId;
    String artist;
    int track;
    long size;
    int duration;
    String path;

    public SongInfo() {
    }

    public SongInfo(long id, String title, String displayName, String albumName, int albumId,
                    String artist, int track, long size, int duration, String path) {
        this.id = id;
        this.title = title;
        this.displayName = displayName;
        this.albumName = albumName;
        this.albumId = albumId;
        this.artist = artist;
        this.track = track;
        this.size = size;
        this.duration = duration;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName == null ? "" : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAlbumName() {
        return albumName == null ? "" : albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getArtist() {
        return artist == null ? "" : artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
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

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "SongInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", displayName='" + displayName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", albumId=" + albumId +
                ", artist='" + artist + '\'' +
                ", track=" + track +
                ", size=" + size +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                '}';
    }
}