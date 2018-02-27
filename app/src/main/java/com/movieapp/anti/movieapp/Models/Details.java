package com.movieapp.anti.movieapp.Models;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by Anti on 2/18/2018.
 */

public class Details implements Serializable {

   public String overview;


//    public  ArrayList<Details> credits = new ArrayList<>();


//    public ArrayList<Credits> credits = new ArrayList<>();


    public Details(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
