package com.movieapp.anti.movieapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.anti.movieapp.Adapters.PeopleRecyclerViewAdapter;
import com.movieapp.anti.movieapp.Api.ApiService;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.Models.People;
import com.movieapp.anti.movieapp.Models.PeopleDataModel;
import com.movieapp.anti.movieapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleActivity extends AppCompatActivity {

    ApiService service;
    RestApi api2;

    @BindView(R.id.recycle2)
    RecyclerView recyler2;

    PeopleDataModel model2;

    PeopleRecyclerViewAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_recycler);


        ButterKnife.bind(this);

//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        recyler2.setLayoutManager(new LinearLayoutManager(this));


        api2 = new RestApi(PeopleActivity.this);
        api2.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<PeopleDataModel> call = api2.getPeople();

                call.enqueue(new Callback<PeopleDataModel>() {
                    @Override
                    public void onResponse(Call<PeopleDataModel> call, Response<PeopleDataModel> response) {
                        if (response.code() == 200) {

                            model2 = response.body();

                            adapter2=new PeopleRecyclerViewAdapter(PeopleActivity.this,model2);

                            adapter2.setItems(model2.results);
                            recyler2.setHasFixedSize(true);
                            recyler2.setAdapter(adapter2);


                        }

                        else if (response.code() == 401) {

                            Toast.makeText(PeopleActivity.this, "Greska na konekcija", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<PeopleDataModel> call, Throwable t) {
                        Toast.makeText(PeopleActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }





}
