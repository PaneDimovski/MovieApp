package com.movieapp.anti.movieapp.Activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.movieapp.anti.movieapp.Adapters.ExlorerRecyclerViewAdapter;
import com.movieapp.anti.movieapp.Api.ApiService;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.Listeners.OnRowClickListener;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.Preferences.PrefererencesManager2;
import com.movieapp.anti.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatedActivity extends AppCompatActivity {

    RestApi api;
    ApiService service;

    MovieDataModel model;

    @BindView(R.id.recycle)
    RecyclerView recycler;

    ExlorerRecyclerViewAdapter adapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rated);

        ButterKnife.bind(this);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        recycler.setLayoutManager(layoutManager);


        final String sessionID = PrefererencesManager2.getSessionID(this);

         api = new RestApi(this);

        Call<MovieDataModel> call = api.getRateds(sessionID);
        call.enqueue(new Callback<MovieDataModel>() {
            @Override
            public void onResponse(Call<MovieDataModel> call, Response<MovieDataModel> response) {
                if(response.code() == 200)
                {
                 model = response.body();
                    adapter = new ExlorerRecyclerViewAdapter(RatedActivity.this, model, new OnRowClickListener() {
                        @Override
                        public void OnRowClick(Movie film, int pozicija) {

                        }
                    });
                    adapter.setItems(model.results);
                    recycler.setHasFixedSize(true);

                    recycler.setAdapter(adapter);

                }
                else  if (response.code() == 401){

                    Toast.makeText(RatedActivity.this, "U are not Log In!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDataModel> call, Throwable t) {
                Toast.makeText(RatedActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });


    }


}
