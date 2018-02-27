package com.movieapp.anti.movieapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieapp.anti.movieapp.Models.Cast;
import com.movieapp.anti.movieapp.Models.Credits;
import com.movieapp.anti.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anti on 2/19/2018.
 */

public class CastRecycler extends RecyclerView.Adapter<CastRecycler.ViewHolder> {

    ArrayList<Cast> CastList = new ArrayList<>();
    Context context;


    public CastRecycler(ArrayList<Cast> castList) {
        this.CastList = castList;
    }


    public CastRecycler(Context context, Credits creditsModel) {

        this.context = context;
        CastList = creditsModel.cast;
    }

    public void setItems(ArrayList<Cast> castData) {

        this.CastList = castData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_cast, parent, false);
        CastRecycler.ViewHolder viewHolder = new CastRecycler.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cast castData = CastList.get(position);
        holder.svezdi.setText(castData.getName());
        holder.opis.setText(castData.getCharacter());

        Picasso.with(context)

                .load("https://image.tmdb.org/t/p/w500/"+castData.getProfile_path())

                .into(holder.slika);


    }

    @Override
    public int getItemCount() {
        return CastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.slika)
        ImageView slika;

        @BindView(R.id.tekst)
        TextView svezdi;

        @BindView(R.id.desc)
        TextView opis;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
