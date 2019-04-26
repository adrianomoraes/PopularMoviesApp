package com.example.android.popularmoviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.GridItemViewHolder> {

    private List<String> mImageList;
    private Context mContext;

    public class GridItemViewHolder extends RecyclerView.ViewHolder {
        //PosterImageView posterImageView;
        ImageView posterImageView;

        public GridItemViewHolder(View view) {
            super(view);
            posterImageView = view.findViewById(R.id.im_poster);
        }
    }

    public ImageGridAdapter(Context context, List imageList) {
        this.mContext = context;
        this.mImageList = imageList;
    }

    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_view, parent, false);

        return new GridItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridItemViewHolder holder, int position) {
        final String path = mImageList.get(position);

        Picasso.get()
                .load(path)
                .resize(185, 277)
                .centerCrop()
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
        return mImageList.size();
    }
}
