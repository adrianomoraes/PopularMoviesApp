package com.example.android.popularmoviesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscoverResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.GridItemViewHolder> {

    private List<String> mImageList;
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

    public ImageGridAdapter(Context context, List<String> imageList, ArrayList<RetroTMDBDiscoverResults> results) {
        this.mContext = context;
        this.mImageList = imageList;
        this.mResults = results;
    }

    @NonNull
    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_view, parent, false);

        return new GridItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridItemViewHolder holder, int position) {
        //final String path = mImageList.get(position);

        final String path = mResults.get(position).getPoster_path();

        Picasso.get()
                .load(path)
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
        //return mImageList.size();
        return mResults.size();
    }
}
