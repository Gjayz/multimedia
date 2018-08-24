package com.gjayz.multimedia.music.bean;

public class Album {

    int album_id;
    String album;
    int artist_id;
    String artist;

    public Album(int album_id, String album, int artist_id, String artist) {
        this.album_id = album_id;
        this.album = album;
        this.artist_id = artist_id;
        this.artist = artist;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getAlbum() {
        return album == null ? "" : album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist() {
        return artist == null ? "" : artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" +
                "album_id=" + album_id +
                ", album='" + album + '\'' +
                ", artist_id=" + artist_id +
                ", artist='" + artist + '\'' +
                '}';
    }
}
