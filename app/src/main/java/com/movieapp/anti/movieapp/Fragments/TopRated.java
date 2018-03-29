package com.movieapp.anti.movieapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movieapp.anti.movieapp.Adapters.ExlorerRecyclerViewAdapter;
import com.movieapp.anti.movieapp.Api.ApiService;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.Listeners.OnRowClickListener;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anti on 12/5/2017.
 */

public class TopRated extends Fragment {

    RestApi api;
    ApiService service;

    MovieDataModel model;

    @BindView(R.id.recycle)
    RecyclerView recycler;

    ExlorerRecyclerViewAdapter adapter;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.popular, null);

        mUnbinder = ButterKnife.bind(this, view);

//        ButterKnife.bind(this, view);

//       RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(), 4);


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        // Create a custom SpanSizeLookup where the first item spans both columns
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position == 0 ? 2 : 1;
//            }
//        });


        recycler.setLayoutManager(layoutManager);

        api = new RestApi(getContext());
        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<MovieDataModel> call = api.getTop();
                call.enqueue(new Callback<MovieDataModel>() {
                    @Override
                    public void onResponse(Call<MovieDataModel> call, Response<MovieDataModel> response) {
                        if (response.code() == 200) {

                            model = response.body();
                            adapter = new ExlorerRecyclerViewAdapter(getActivity(), model, new OnRowClickListener() {
                                @Override
                                public void OnRowClick(Movie film, int pozicija) {

                                }
                            });
                            adapter.setItems(model.results);
                            recycler.setHasFixedSize(true);

                            recycler.setAdapter(adapter);

                        }

                        else if (response.code() == 401) {

                            Toast.makeText(getContext(), "401 Error !", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDataModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        return view;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
