package com.movieapp.anti.movieapp.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.movieapp.anti.movieapp.Api.ApiService;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.Models.*;
import com.movieapp.anti.movieapp.Models.Rated;
import com.movieapp.anti.movieapp.Others.PrefererencesManager;
import com.movieapp.anti.movieapp.Preferences.PrefererencesManager2;
import com.movieapp.anti.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActiviti extends AppCompatActivity {

    ApiService service;
    RestApi api2;
    int pozicija;

    @BindView(R.id.rate)
    public ImageView rateslika;
    @BindView(R.id.slika_detali)
    ImageView slika;

    @BindView(R.id.textReziser)
    TextView reziser;

    @BindView(R.id.textScenario)
    TextView scenario;

    @BindView(R.id.textSvezdi)
    TextView svezdi;

    @BindView(R.id.textGore)
    TextView tekstGore;

    @BindView(R.id.kopceEkipa)
    Button kopce;

    @BindView(R.id.textOpis)
    TextView opis;

    @BindView(R.id.slikica)
    ImageView slikica;

    @BindView(R.id.watch)
    public ImageView watchSlika;



    Credits credits;
    Context context;
    Crew crew;
    Movie model;

    Cast cast;
    String director;
    String screenplay;

    VideoModel videoModel;
    Video video;

    Rated ratemodel = new Rated();
    RatedList ratedList;

    final Rated rated = new Rated();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        ButterKnife.bind(this);
        context = this;


        final Intent intent = getIntent();
        if (intent.hasExtra("Details")) {
            pozicija = intent.getIntExtra("Details", 0);

            GetMovie();
        }


//

    }

    @OnClick(R.id.trailer)
    public void kli5(View t) {


        RestApi api3 = new RestApi(context);

        final Call<VideoModel> videoModelCall = api3.getVideo(model.id);

        videoModelCall.enqueue(new Callback<VideoModel>() {

            @Override

            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {

                if (response.isSuccessful()) {

                    videoModel = response.body();

                    video = videoModel.results.get(0);

                    if (video == null) {

                        video = videoModel.results.get(1);

                    } else if (video == null) {

                        video = videoModel.results.get(2);

                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + video.key));

                    startActivity(intent);

                }

            }

            @Override

            public void onFailure(Call<VideoModel> call, Throwable t) {

            }

        });
    }

    @OnClick(R.id.rate)
    public void klik2(View v2) {
        AddRate();
    }

    @OnClick(R.id.slikica)
    public void klik3(View v3) {
        AddFavorites();
    }

    @OnClick(R.id.watch)
    public void klik4(View v4) {
        addWatch();
    }

    @OnClick(R.id.kopceEkipa)
    public void klik(View v) {

        Intent intent2 = new Intent(DetailsActiviti.this, FullCast.class);
        intent2.putExtra("Cast", pozicija);
        startActivity(intent2);
    }

    public void GetMovie() {

        RestApi api = new RestApi(this);

        Call<Movie> call = api.getMovie(pozicija, "credits");

        call.enqueue(new Callback<Movie>() {

            @Override

            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if (response.code() == 200) {

                    ArrayList<Crew> writers = new ArrayList<>();
                    ArrayList<Cast> stars = new ArrayList<>();


                    model = response.body();

                    GetFavorite();

                    if (model.overview != null) {

                        opis.setText(model.overview);

                    } else {

                        opis.setText("No overview available");

                    }

                    if (model.credits.crew.size() > 0) {

                        for (int i = 0; i < 1; i++) {

                            crew = model.credits.crew.get(i);

                            reziser.setText("Director: " + crew.getName());

                        }

                    }

                    for (Crew crew : model.credits.crew) {

                        if (crew.department.equals("Writing")) {

                            writers.add(crew);

                        } else {

                            tekstGore.setText("");

                        }

                    }

                    if (writers.size() == 1) {

                        crew = writers.get(0);

                        tekstGore.setText(crew.name);

                    } else if (writers.size() > 1) {

                        for (int i = 0; i < 2; i++) {

                            crew = writers.get(i);

                            if (writers.size() > 0) {

                                tekstGore.setText(tekstGore.getText().toString() + crew.name + ", ");

                            }

                        }

                    }

                    tekstGore.setText("Writers: " + tekstGore.getText().toString());

                    if (model.credits.cast.size() >= 3) {

                        for (int i = 0; i < 3; i++) {

                            cast = model.credits.cast.get(i);

                            if (cast != null) {

                                stars.add(cast);

                            }

                        }

                    } else if (model.credits.cast.size() == 2) {

                        for (int i = 0; i < 2; i++) {

                            cast = model.credits.cast.get(i);

                            if (cast != null) {

                                stars.add(cast);

                            }

                        }

                    } else {

                        if (cast != null) {

                            stars.add(cast);

                        }

                    }

                    if (stars.size() > 0) {

                        for (int i = 0; i < stars.size(); i++) {

                            cast = stars.get(i);

                            if (stars.size() > 0) {

                                svezdi.setText(svezdi.getText().toString() + cast.getName() + ", ");

                            }

                        }

                        svezdi.setText("Stars: " + svezdi.getText().toString());

                    } else {

                        svezdi.setText("Stars: ");

                    }

//                        toolbarLayout.setTitle(model.title);
//
//                        titleMovie.setText(model.title);

                    String path = "http://image.tmdb.org/t/p/w185" + model.getPoster_path();

                    Picasso.with(context).load(path).centerInside().fit().into(slika);
//
                }
            }

            @Override

            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


    }


    public void AddFavorites() {

        final String sessionID = PrefererencesManager2.getSessionID(context);
        FavoriteMoviePost movieResponse = new FavoriteMoviePost();
        movieResponse.media_type = "movie";
        movieResponse.media_id = model.id;
        movieResponse.favorite = true;

        RestApi api2 = new RestApi(context);

        Call<Movie> call2 = api2.addFavorites(sessionID, "json/application", movieResponse);

        call2.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    model = response.body();

                    Picasso.with(context).load("@mipmap/favourites_full_mdpi").centerInside().fit().into(slikica);
                } else if (response.code() == 200) {

                    if (model.favorite == true) {
                        Toast.makeText(DetailsActiviti.this, "Already added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Toast.makeText(DetailsActiviti.this, "Error, cannot apply to Favorites", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void GetFavorite() {

        String sessionID2 = PrefererencesManager2.getSessionID(context);

        RestApi api3 = new RestApi(context);


        Call<Movie> call2 = api3.getStateInfo(model.id, sessionID2);

        call2.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    model = response.body();

                    if (model.favorite == true) {

                        Picasso.with(context).load(R.drawable.favourites_full).centerInside().fit().into(slikica);
                    } else if (model.favorite == false) {
                        Picasso.with(context).load(R.drawable.favourites_empty).centerInside().fit().into(slikica);
                    }

                    if (model.rated.value > 0) {
                        Picasso.with(context).load(R.drawable.ic_menu_camera).centerInside().fit().into(slikica);
                    } else {
                        Picasso.with(context).load(R.drawable.rate_empty).centerInside().fit().into(slikica);
                    }

                    if (model.watchlist == true) {

                        Picasso.with(context).load(R.drawable.watchlist_remove).centerInside().fit().into(slikica);
                    } else {
                        Picasso.with(context).load(R.drawable.watchlist_add).centerInside().fit().into(slikica);
                    }

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Toast.makeText(context, "Error connection", Toast.LENGTH_SHORT).show();


            }
        });
    }





    public void addWatch() {

        WatchListMoviePost watch = new WatchListMoviePost();

        String sessionID4 = PrefererencesManager2.getSessionID(context);
        RestApi api5 = new RestApi(this);
        watch.media_id = model.id;
        watch.media_type = "movie";
        watch.watchlist = true;

        Call<Movie> call5 = api5.addWatchlist(sessionID4, "json/application", watch);
        call5.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    model = response.body();
                    Toast.makeText(context, "U add movie to watchList :)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


    }

    public void AddRate() {


        String sessionID = PrefererencesManager2.getSessionID(context);

        float rate = 5;
        final Rated rated = new Rated();
        rated.value = rate;

        int movieID = model.id;



        RestApi api4 = new RestApi(context);

        Call<Movie> call4 = api4.AddRateMovie(movieID, "json/application", sessionID, rated);

        call4.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    model = response.body();
                    ratedList.ratedMovies.add(rated);
                    Toast.makeText(context, "U RatedActivity the movie with 5 stars :)", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


    }


}


