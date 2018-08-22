package com.gjayz.multimedia.music.bean;

import java.util.ArrayList;
import java.util.List;

public class School {

    int id;
    String name;
    List<SongInfo> song_infos;

    public School(int id, String name, List<SongInfo> album_id) {
        this.id = id;
        this.name = name;
        this.song_infos = album_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SongInfo> getAlbum_id() {
        if (song_infos == null) {
            return new ArrayList<>();
        }
        return song_infos;
    }

    public void setAlbum_id(List<SongInfo> album_id) {
        this.song_infos = album_id;
    }
}
