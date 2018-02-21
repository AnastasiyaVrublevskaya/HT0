package com.training;

import java.util.ArrayList;

public class Author {

    private String name;
    private ArrayList<Album> albums = new ArrayList<Album>();

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
}
