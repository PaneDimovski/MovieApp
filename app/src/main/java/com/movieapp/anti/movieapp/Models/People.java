package com.movieapp.anti.movieapp.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Anti on 2/12/2018.
 */

public class People implements Serializable {



    String name;
    int id;
    String profile_path;


    ArrayList<KnownFor> known_for = new ArrayList<>();


    public People() {
    }

    public ArrayList<KnownFor> getKnownFors() {
        return known_for;
    }

    public People(String name, int id, String profile_path) {

        this.name = name;
        this.id = id;
        this.profile_path = profile_path;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
