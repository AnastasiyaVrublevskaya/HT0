package com.training;

import java.util.ArrayList;

public class Album {

    private String albumTitle;
    private ArrayList<Song> songs = new ArrayList<Song>();

    public Album(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
