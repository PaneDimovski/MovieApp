package com.movieapp.anti.movieapp.Activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.movieapp.anti.movieapp.Adapters.CastRecycler;
import com.movieapp.anti.movieapp.Adapters.PeopleRecyclerViewAdapter;
import com.movieapp.anti.movieapp.Api.ApiService;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.Models.Cast;
import com.movieapp.anti.movieapp.Models.Crew;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.PeopleDataModel;
import com.movieapp.anti.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullCast extends AppCompatActivity {

    @BindView(R.id.recycle3)
    RecyclerView recycler3;
Context context;

    Movie model;
    Cast cast;
    int pozicija;
    CastRecycler adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cast_recycler);


        ButterKnife.bind(this);

        final Intent intent = getIntent();
        if (intent.hasExtra("Cast")) {
            pozicija = intent.getIntExtra("Cast", 0);
            GetCast();
        }


    }

    public void GetCast() {

        recycler3.setLayoutManager(new LinearLayoutManager(this));

        RestApi api = new RestApi(FullCast.this);

        Call<Movie> call = api.getMovie(pozicija, "credits");

        call.enqueue(new Callback<Movie>() {

            @Override

            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if (response.code() == 200) {

                    ArrayList<Crew> writers = new ArrayList<>();
                    ArrayList<Cast> stars = new ArrayList<>();

                    model = response.body();
                    adapter2 = new CastRecycler(context, model.credits);
                    adapter2.setItems(model.credits.cast);
                    recycler3.setHasFixedSize(true);
                    recycler3.setAdapter(adapter2);

                } else if (response.code() == 401) {

                    Toast.makeText(FullCast.this, "Greska na konekcija", Toast.LENGTH_SHORT).show();

                }
            }

            @Override

            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }

}




