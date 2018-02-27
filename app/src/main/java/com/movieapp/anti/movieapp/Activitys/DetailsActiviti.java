package com.movieapp.anti.movieapp.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.movieapp.anti.movieapp.Models.Cast;
import com.movieapp.anti.movieapp.Models.Credits;
import com.movieapp.anti.movieapp.Models.Crew;
import com.movieapp.anti.movieapp.Models.Details;
import com.movieapp.anti.movieapp.Models.FavoriteMoviePost;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.Models.RateMoviePost;
import com.movieapp.anti.movieapp.Models.WatchListMoviePost;
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
    ImageView rateslika;
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
    ImageView watchSlika;


    @BindView(R.id.slikica2)
    ImageView slikica2;


    Credits credits;
    Context context;
    Crew crew;
    Movie model;

    Cast cast;
    String director;
    String screenplay;


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

                    GetFavorites();

                    model = response.body();

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

//                        genreAdapter = new RecyclerViewGenretAdapter(ScrollingMovieDetailActivity.this, new OnRowGenreClickListener() {
//
//                            @Override
//
//                            public void onRowClick(Genre genre, int position) {
//
//                                Intent intent = new Intent(ScrollingMovieDetailActivity.this, ExploreActivity.class);
//
//                                intent.putExtra("GID", genre.id);
//
//                                intent.putExtra("name", genre.name);
//
//                                startActivityForResult(intent, 1212);
//
//                            }
//
//                        });

//                        genreAdapter.setItems(model.genres);
//
//                        genresRV.setHasFixedSize(true);
//
//                        genresRV.setLayoutManager(new LinearLayoutManager(ScrollingMovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
//
//                        genresRV.setAdapter(genreAdapter);
//
//                        WatchlistListener();
//
//                        FavoriteListener();

                }
            }

            @Override

            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


        watchSlika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWatch();
            }
        });


        rateslika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rate();
            }
        });


        slikica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                        Toast.makeText(DetailsActiviti.this, "Error, cannot apply to Favorites", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }

    public void GetFavorites() {

        String sessionID2 = PrefererencesManager2.getSessionID(context);

        RestApi api3 = new RestApi(context);


        Call<Movie> call2 = api3.getFavorit(pozicija, sessionID2);

        call2.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    model = response.body();

                    if (model.favorite == true) {

                        Picasso.with(context).load("@mipmap/favourites_full_mdpi").centerInside().fit().into(slikica);
                    } else {
                        Picasso.with(context).load("@mipmap/favourites_empty_mdpi").centerInside().fit().into(slikica);
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(DetailsActiviti.this, "Error, cannot apply to Favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Rate() {
        RateMoviePost rateMoviePost = new RateMoviePost();


        String sessionID = PrefererencesManager2.getSessionID(context);

        rateMoviePost.value = 5;
        RestApi api4 = new RestApi(context);

        Call<Movie> call4 = api4.rateMovie(pozicija, "json/application", sessionID, rateMoviePost);
        call4.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {

                    model = response.body();
                    Toast.makeText(context, "U Rated the movie with 5 stars :)", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


    }

    void addWatch() {

        WatchListMoviePost watch = new WatchListMoviePost();

        String sessionID4 = PrefererencesManager2.getSessionID(context);
      RestApi api5 = new RestApi(this);
        watch.media_id = model.id;
        watch.media_type = "movie";
        watch.watchlist = true;

        Call<Movie> call5 = api5.addWatchlist(sessionID4,"json/application",watch);
        call5.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code()== 200)  {

                    model= response.body();
                    Toast.makeText(context, "U add movie to watchList :)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


    }


}


