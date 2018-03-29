package com.movieapp.anti.movieapp.Api;


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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @GET("movie/popular?" + ApiConstants.ConsumerKey)
    Call<MovieDataModel> getPopular();

    @GET("movie/top_rated?" + ApiConstants.ConsumerKey)
    Call<MovieDataModel> getTop();

    @GET("movie/upcoming?" + ApiConstants.ConsumerKey)
    Call<MovieDataModel> getUpcoming();

    @GET("movie/now_playing?" + ApiConstants.ConsumerKey)
    Call<MovieDataModel> getPlaying();

    @GET("person/popular?" + ApiConstants.ConsumerKey)
    Call<PeopleDataModel> getPeople();


    @GET("movie/{movie_id}?" + ApiConstants.ConsumerKey)
    Call<Movie> getMovie(@Path("movie_id") int link, @Query("append_to_response") String credits);


    @GET("authentication/token/new?" + ApiConstants.ConsumerKey)
    Call<Token> getToken(@Query ("request_token") String request_code);

    @GET("authentication/token/validate_with_login?" + ApiConstants.ConsumerKey)
    Call<Token> GetLogin (@Query("username")String username , @Query ("password") String password, @Query ("request_token") String request_code) ;

    @GET("authentication/session/new?" + ApiConstants.ConsumerKey)
    Call<Token> getSesionId (@Query ("request_token") String request_code) ;


    @POST("account/account_id/favorite?" + ApiConstants.ConsumerKey)
    Call<Movie> addFavorites(@Query("session_id") String sessionId,@Header("json/application") String header, @Body FavoriteMoviePost movieResponse);

    @POST("account/account_id/watchlist?" + ApiConstants.ConsumerKey)
    Call<Movie> addWatchlist(@Query("session_id") String sessionId,@Header("json/application") String header, @Body WatchListMoviePost watchListMoviePost);

    @POST("movie/{movie_id}/rating?" + ApiConstants.ConsumerKey)
    Call<Movie> addRating(@Path("movie_id") int id, @Header("json/application") String header, @Query("session_id") String sessionId, @Body Rated rated);

    @GET("account/account_id/favorite/movies?" + ApiConstants.ConsumerKey)
    Call<MovieDataModel> getFavorites (@Query ("session_id") String sessionId) ;    // Mnozina

    @GET(" movie/{movie_id}/account_states?" + ApiConstants.ConsumerKey)
    Call<Movie> getStateInfo (@Path("movie_id") int id, @Query("session_id") String sessionId);    // Details ednina za slikata




    @GET("account/account_id/rated/movies?" + ApiConstants.ConsumerKey)
    Call<MovieDataModel> getRateds (@Query ("session_id") String sessionId) ;    // Mnozina



    @GET("account/account_id/watchlist/movies?" + ApiConstants.ConsumerKey)
    Call<MovieDataModel> getWatchlists (@Query ("session_id") String sessionId) ;    // Mnozina


    @GET("account?" + ApiConstants.ConsumerKey)
    Call<User> getUserDetails(@Query("session_id") String sessionId);  // User Detali

    @GET("movie/{movie_id}/videos?" + ApiConstants.ConsumerKey)
    Call<VideoModel> getVideo(@Path("movie_id") int pozicija);


    @GET("search/movie?" + ApiConstants.ConsumerKey)

    Call<MovieDataModel> getSearchMovie (@Query ("query") String query) ;

    @GET("account/{account_id}/favorite/movies?" + ApiConstants.ConsumerKey)

    Call<MovieDataModel> getUserFavorites(@Path("account_id") String account_id,@Query("session_id") String session_id);

    @GET("account?" + ApiConstants.ConsumerKey)
    Call<User> getUserDetails2(@Query("session_id") String sessionId);  // User Detali

}
