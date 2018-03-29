package com.movieapp.anti.movieapp.Models;

import java.io.Serializable;

/**
 * Created by Anti on 2/10/2018.
 */

public class Movie implements Serializable {

    public String original_title;
    public int id;
    public String poster_path;
    public String overview;

    public Boolean favorite;
    public transient  Boolean favorites;

    public Boolean watchlist;


   public Rated rated;

//    public Boolean getRated() {
//        return rated;
//    }
//
//    public void setRated(Boolean rated) {
//        this.rated = rated;
//    }

    public Boolean getFavorites() {
        return favorites;
    }

    public void setFavorites(Boolean favorites) {
        this.favorites = favorites;
    }

    public Credits credits;

    public Movie() {
    }

    public Movie(String original_title, int id, String poster_path) {
        this.original_title = original_title;
        this.id = id;
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
