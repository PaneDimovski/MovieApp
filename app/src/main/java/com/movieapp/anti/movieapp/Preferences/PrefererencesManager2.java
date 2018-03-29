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

    public static void  addUser(String Name, Context c) {


        getPreferences(c).edit().putString("name",Name).apply();

    }

    public static String getUser( Context c) {

        return getPreferences(c).getString( "name","");
    }
    public static String setUserID( Context c) {

        return getPreferences(c).getString( "name","");
    }

    public static void  addtoken (String token, Context c) {

        getPreferences(c).edit().putString("token",token).apply();

    }

    public static String getToken (Context c) {

        return  getPreferences(c).getString("token","");
    }

    public static void removeToken(Context c) {

        getPreferences(c).edit().remove("token").apply();

    }

    public static void removeUserID(Context c) {

        getPreferences(c).edit().remove("UserLogin").apply();

    }


    public static void addSessionID (String Email, Context c)  {

        getPreferences(c).edit().putString("SessionID", Email).apply();
    }

    public static void removeSessionID(Context c) {

        getPreferences(c).edit().remove("SessionID").apply();

    }

// Oznaka kako za folder pod koj se zapisuvaat nekoi fajlovi stringovi

    public static  String getSessionID (Context c) {

        return getPreferences(c).getString("SessionID", "");
    }







}
