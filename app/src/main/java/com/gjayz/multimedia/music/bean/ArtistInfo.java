package com.gjayz.multimedia.music.bean;

import java.util.ArrayList;
import java.util.List;

public class ArtistInfo {

    int artist_id;
    String artist;
    int num_tracks;
    int num_albums;
    List<Integer> album_ids;


    public ArtistInfo(int artist_id, String artist, int num_tracks, int num_albums, List<Integer> album_ids) {
        this.artist_id = artist_id;
        this.artist = artist;
        this.num_tracks = num_tracks;
        this.num_albums = num_albums;
        this.album_ids = album_ids;
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

    public int getNum_tracks() {
        return num_tracks;
    }

    public void setNum_tracks(int num_tracks) {
        this.num_tracks = num_tracks;
    }

    public int getNum_albums() {
        return num_albums;
    }

    public void setNum_albums(int num_albums) {
        this.num_albums = num_albums;
    }

    public List<Integer> getAlbum_ids() {
        if (album_ids == null) {
            return new ArrayList<>();
        }
        return album_ids;
    }

    public void setAlbum_ids(List<Integer> album_ids) {
        this.album_ids = album_ids;
    }

    @Override
    public String toString() {
        return "ArtistInfo{" +
                "artist_id=" + artist_id +
                ", artist='" + artist + '\'' +
                ", num_tracks=" + num_tracks +
                ", num_albums=" + num_albums +
                ", album_ids=" + album_ids +
                '}';
    }
}