package com.movieapp.anti.movieapp.Models;

import java.io.Serializable;

/**
 * Created by Anti on 2/20/2018.
 */

public class User implements Serializable {

    public String username;
    public String password;
    public String name;
    public Avatar avatar;
    public String guest;
    public String id;


//    public User(String username, String name) {
//        this.username = username;
//        this.name = name;
//    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
