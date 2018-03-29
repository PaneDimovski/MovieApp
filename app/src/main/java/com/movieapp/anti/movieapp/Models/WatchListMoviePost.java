package com.movieapp.anti.movieapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Anti on 2/26/2018.
 */

public class WatchListMoviePost implements Serializable {



    public String media_type;

    @SerializedName("media_id")

    public int media_id;

    @SerializedName("watchlist")

    public boolean watchlist;



}
