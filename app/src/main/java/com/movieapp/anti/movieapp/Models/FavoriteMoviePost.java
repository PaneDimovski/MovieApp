package com.movieapp.anti.movieapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Anti on 2/22/2018.
 */

public class FavoriteMoviePost implements Serializable {

    @SerializedName("media_type")

    public String media_type;

    @SerializedName("media_id")

    public int media_id;

    @SerializedName("favorite")

    public boolean favorite;




}
