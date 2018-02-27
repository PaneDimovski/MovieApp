package com.movieapp.anti.movieapp.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


import com.google.gson.Gson;
import com.movieapp.anti.movieapp.Models.Token;
import com.movieapp.anti.movieapp.Models.User;


public class PrefererencesManager2 {

    private static final String USERID = "USERID";

    private static final String SESIONID = "sesionID";

    private static SharedPreferences getPreferences (Context c) {

        return c.getApplicationContext().getSharedPreferences("MySharedPreffsFile", Activity.MODE_PRIVATE);

    }

    public static void  addUserID (String Email, Context c) {


        getPreferences(c).edit().putString("UserLogin",Email).apply();

    }

    public static Token getToken (Context c) {

        return  new Gson().fromJson(getPreferences(c).getString("token", "" ),Token.class);
    }

    public static String getUserid(Token token, Context c) {

        return getPreferences(c).getString( "UserLogin","");
    }

    public static void addSessionID (String Email, Context c)  {

        getPreferences(c).edit().putString("SessionID", Email).apply();
    }

// Oznaka kako za folder pod koj se zapisuvaat nekoi fajlovi stringovi

    public static  String getSessionID (Context c) {

        return getPreferences(c).getString("SessionID", "");
    }






}
