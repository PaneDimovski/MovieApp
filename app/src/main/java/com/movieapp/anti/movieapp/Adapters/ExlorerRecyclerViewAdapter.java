package com.movieapp.anti.movieapp.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.anti.movieapp.Activitys.DetailsActiviti;
import com.movieapp.anti.movieapp.Activitys.Favorites;
import com.movieapp.anti.movieapp.Api.ApiService;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.Listeners.OnRowClickListener;
import com.movieapp.anti.movieapp.Models.Details;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.Others.PrefererencesManager;
import com.movieapp.anti.movieapp.Preferences.PrefererencesManager2;
import com.movieapp.anti.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Anti on 1/19/2018.
 */

public class ExlorerRecyclerViewAdapter extends RecyclerView.Adapter<ExlorerRecyclerViewAdapter.ViewHolder> {

    List<Movie> movieDataList = new ArrayList<>();

    Context context;
    OnRowClickListener onRowClickListener;

    public ExlorerRecyclerViewAdapter(List<Movie> movieDataList) {

        this.movieDataList = movieDataList;

    }

    public ExlorerRecyclerViewAdapter(Context context, MovieDataModel movieModel, OnRowClickListener onRowClickListener) {

        this.context = context;
        this.onRowClickListener= onRowClickListener;
        movieDataList = movieModel.results;
    }

    public void setItems(List<Movie> movieData) {

        this.movieDataList = movieData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popular_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movieData = movieDataList.get(position);

        holder.naziv.setText(movieData.getOriginal_title());
        String path = "http://image.tmdb.org/t/p/w500" + movieData.getPoster_path();

        Picasso.with(context).load(path).fit().into(holder.poster);




    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.naziv)
        TextView naziv;

        @BindView(R.id.poster)
        ImageView poster;
        RestApi api;
        ApiService service;
        Movie model;
        int pos;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    Movie clickedDataItem = movieDataList.get(pos);

                    Intent intent = new Intent(context, DetailsActiviti.class);
                    intent.putExtra("Details", movieDataList.get(pos).getId());
                    context.startActivity(intent);
                }
            });


        }
    }
}