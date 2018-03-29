package com.movieapp.anti.movieapp.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.movieapp.anti.movieapp.Activitys.Login;
import com.movieapp.anti.movieapp.BuildConfig;
import com.movieapp.anti.movieapp.Models.Credits;
import com.movieapp.anti.movieapp.Models.Details;
import com.movieapp.anti.movieapp.Models.FavoriteMoviePost;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.Models.PeopleDataModel;
import com.movieapp.anti.movieapp.Models.RateMoviePost;
import com.movieapp.anti.movieapp.Models.Rated;
import com.movieapp.anti.movieapp.Models.Token;
import com.movieapp.anti.movieapp.Models.User;
import com.movieapp.anti.movieapp.Models.VideoModel;
import com.movieapp.anti.movieapp.Models.WatchListMoviePost;
import com.movieapp.anti.movieapp.Others.CheckInternetConn;
import com.movieapp.anti.movieapp.Others.LoggingInterceptor;
import com.movieapp.anti.movieapp.Others.PrefererencesManager;
import com.movieapp.anti.movieapp.Preferences.PrefererencesManager2;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApi {

    public static final int request_max_time_in_secconds = 20;

    private Context activity;

    public RestApi(Context activity) {
        this.activity = activity;
    }

    public Retrofit getRetrofitInstance() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor(activity, PrefererencesManager2.getToken(activity)))
                .readTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


    }

    public ApiService request() {


        return getRetrofitInstance().create(ApiService.class);

    }


    public void checkInternet(Runnable callback) {
        if (CheckInternetConn.CheckInternetConnectivity(activity, true, callback))
            callback.run();
    }

    public void checkInternet(Runnable callback, boolean showConnectionDialog) {
        if (CheckInternetConn.CheckInternetConnectivity(activity, showConnectionDialog, callback))
            callback.run();
        else {
            Toast.makeText(activity, "Connection failed, please check your connection settings", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkInternet(Runnable callback, boolean showConnectionDialog, String message) {
        if (CheckInternetConn.CheckInternetConnectivity(activity, showConnectionDialog, callback))
            callback.run();
        else {
            if (showConnectionDialog)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            else
                Log.d("Connection failed", "" + message);
        }
    }


    public Call<MovieDataModel> getPopular() {
        return request().getPopular();
    }

    public Call<MovieDataModel> getTop() {
        return request().getTop();
    }


    public Call<MovieDataModel> getUpcoming() {
        return request().getUpcoming();
    }

    public Call<MovieDataModel> getPlaying() {
        return request().getPlaying();
    }

    public Call<PeopleDataModel> getPeople() {
        return request().getPeople();
    }


    public Call<Movie> getMovie(int id,String append_to_response)
    {
        return request().getMovie(id,append_to_response);
    }

    public Call<Token> getToken(String request_token) {

        return request().getToken(request_token);
    }
    public Call<Token> GetLogin(String username, String password, String request) {

        return request().GetLogin(username, password, request);
    }

    public Call<Token> getSesionId(String request) {

        return request().getSesionId( request);
    }
    public Call<Movie> addFavorites(String sessionId, String header, FavoriteMoviePost movieResponse) {

        return request().addFavorites( sessionId, header, movieResponse);
    }

    public Call<MovieDataModel> getFavorites(String sessionId) {

        return request().getFavorites( sessionId);
    }

    public Call<Movie> getStateInfo(int id,String sessionId)
    {
        return request().getStateInfo(id,sessionId);
    }


    public  Call<Movie> AddRateMovie (int id, String header,String sessionID, Rated rate) {
        return request().addRating(id,header,sessionID,rate);

    }

    public Call<MovieDataModel> getRateds (String sessionId) {
        return request().getRateds(sessionId);
    }

    public  Call<Movie> addWatchlist (String sessionId, String header, WatchListMoviePost watchListMoviePost){

        return request().addWatchlist(sessionId, header, watchListMoviePost);
    }

    public Call<MovieDataModel> getWatchlists(String sessionId) {

        return request().getWatchlists ( sessionId);
    }

    public Call<User> getUserDetails(String sessionId) {

        return request().getUserDetails ( sessionId);
    }

    public Call<VideoModel> getVideo(int id) {
        return request().getVideo(id);
    }

    public Call<MovieDataModel> getSearchMovie(String query) {

        return request().getSearchMovie(query);

    }

    public Call<MovieDataModel> getUserFavorites(String account_id,String session_id)

    {return request().getUserFavorites(account_id,session_id);}

    public Call<User> getUserDetails2(String sessionId) {

        return request().getUserDetails2 ( sessionId);
    }

}