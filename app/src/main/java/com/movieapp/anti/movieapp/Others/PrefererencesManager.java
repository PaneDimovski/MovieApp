package com.movieapp.anti.movieapp.Others;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.movieapp.anti.movieapp.Models.Token;

/**
 * Created by Anti on 1/21/2018.
 */

public class PrefererencesManager {

    private static final String USERID = "USERID";


    private static SharedPreferences getPreferences (Context c) {

        return c.getApplicationContext().getSharedPreferences("MySharedPreffsFile", Activity.MODE_PRIVATE);

    }

// string da se dodade
    public static  void addToken (Token token, Context c) {

        Gson gson = new Gson();
        String mapString = gson.toJson(token);
        getPreferences(c).edit().putString("token",mapString).apply();

    }
    public static Token getToken (Context c) {

        return  new Gson().fromJson(getPreferences(c).getString("token", "" ),Token.class);
    }

//    public static void  addFavorites (FavoritesModel favorites, Context c) {
//
//        Gson gson = new Gson();
//        String mapString = gson.toJson(favorites);
//        getPreferences(c).edit().putString("favorites",mapString).apply();
//
//    }
//
//    public static FavoritesModel getFavorites (Context c) {
//
//        return  new Gson().fromJson(getPreferences(c).getString("favorites", "" ),FavoritesModel.class);
//    }



//    public static void addSetings (Settings settings, Context c){
//
//        Gson gson2 = new Gson();
//        String mapString2 = gson2.toJson(settings);
//        getPreferences(c).edit().putString("limit", mapString2).apply();
//
//    }
//
//    public static Settings getSettings (Context c) {
//
//        return  new Gson().fromJson(getPreferences(c).getString("limit", "" ),Settings.class);
//    }


    public static void setUserID(String firstName, Context c) {

        getPreferences(c).edit().putString(USERID, firstName).apply();

    }



    public static String getUserID(Context c) {

        return getPreferences(c).getString(USERID, "");

    }

    public static void removeUser(Context c) {

        getPreferences(c).edit().remove(USERID).apply();



    }



}
