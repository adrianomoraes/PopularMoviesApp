package com.example.android.popularmoviesapp.views.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscoverResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.GridItemViewHolder> {

    private Context mContext;

    private ArrayList<RetroTMDBDiscoverResults> mResults;

    public class GridItemViewHolder extends RecyclerView.ViewHolder {
        //PosterImageView posterImageView;
        ImageView posterImageView;

        public GridItemViewHolder(View view) {
            super(view);
            posterImageView = view.findViewById(R.id.im_poster);
        }
    }

    public ImageGridAdapter(Context context, ArrayList<RetroTMDBDiscoverResults> results) {
        this.mContext = context;
        this.mResults = results;

    }

    public void setResults (ArrayList<RetroTMDBDiscoverResults> results){
        this.mResults = results;
        notifyDataSetChanged();
    }

    public void clearResults(){
        if (mResults != null) mResults.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_view, parent, false);

        return new GridItemViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(GridItemViewHolder holder, int position) {

        String path = mResults.get(position).getPosterPath();

        Picasso.get()
                .load(path)
                .placeholder(mContext.getDrawable(R.drawable.film_poster_placeholder))
                //.resize(185, 277)
                //.centerCrop()
                .into(holder.posterImageView);

        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle click event on image
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
