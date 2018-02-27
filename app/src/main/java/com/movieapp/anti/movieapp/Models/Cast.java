package com.movieapp.anti.movieapp.Models;

import java.io.Serializable;

/**
 * Created by Anti on 2/18/2018.
 */

public class Cast implements Serializable {

    String name;
    String character;
    String  profile_path;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
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
}
