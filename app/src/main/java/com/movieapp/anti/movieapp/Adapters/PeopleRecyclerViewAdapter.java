package com.movieapp.anti.movieapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieapp.anti.movieapp.Models.KnownFor;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.Models.People;
import com.movieapp.anti.movieapp.Models.PeopleDataModel;
import com.movieapp.anti.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anti on 2/15/2018.
 */

public class PeopleRecyclerViewAdapter extends RecyclerView.Adapter<PeopleRecyclerViewAdapter.ViewHolder> {

    List<People> PeopleList = new ArrayList<>();

        Context context;

    public  PeopleRecyclerViewAdapter (List<People> peopleList) {

        this.PeopleList = peopleList;
    }

    public PeopleRecyclerViewAdapter (Context context, PeopleDataModel peopleModel) {

        this.context = context;
        PeopleList = peopleModel.results;
    }

    public void setItems(List<People> peopleData) {

        this.PeopleList = peopleData;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.people, parent, false);
        PeopleRecyclerViewAdapter.ViewHolder viewHolder = new PeopleRecyclerViewAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        People peopleData = PeopleList.get(position);

        holder.tekst.setText(peopleData.getName());




        Picasso.with(context)

                .load("https://image.tmdb.org/t/p/w500/"+peopleData.getProfile_path())

                .into(holder.slika);


        holder.desc.setText("");

//        for(int i=0; i<peopleData.getKnownFors().size(); i++){
//
//            if(holder.desc.equals("")){
//
//                holder.desc.setText(holder.desc.getText() + " " + peopleData.getKnownFors().get(i).getTitle());
//
//            }
//
//            holder.desc.setText(holder.desc.getText() + ", " + peopleData.getKnownFors().get(i).getTitle());
//
//        }
//
//        holder.tekst.setText(position+1+"");
//
//    }


        for (KnownFor known : peopleData.getKnownFors()  ) {

        holder.desc.setText(holder.desc.getText().toString() + known.getTitle());
        }
    }




    @Override
    public int getItemCount() {
        return PeopleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.slika)
        ImageView slika;

        @BindView(R.id.tekst)
        TextView tekst;

        @BindView(R.id.desc)
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }
}
