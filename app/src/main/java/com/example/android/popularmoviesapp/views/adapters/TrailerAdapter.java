package com.example.android.popularmoviesapp.views.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.views.listeners.CustomItemClickListener;
import com.example.android.popularmoviesapp.views.view_models.TrailerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<TrailerModel> imageModelArrayList;
    private Context mContext;

    CustomItemClickListener mListener;

    public TrailerAdapter(Context ctx, ArrayList<TrailerModel> imageModelArrayList, CustomItemClickListener listener){

        inflater = LayoutInflater.from(ctx);
        mContext = ctx;
        mListener = listener;

        this.imageModelArrayList = imageModelArrayList;
    }

    public void setListener(ImageView imageView, final int pos){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, pos);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_videos, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(TrailerAdapter.MyViewHolder holder, int position) {

        //holder.iv.setImageResource(imageModelArrayList.get(position).getImage_drawable());
        //USE PICASSO


        Picasso.get()
                .load(imageModelArrayList.get(position).getImage_drawable())
                .placeholder(mContext.getDrawable(R.drawable.film_poster_placeholder))
                //.resize(185, 277)
                //.centerCrop()
                .into( holder.iv);
        holder.time.setText(imageModelArrayList.get(position).getName());


        setListener(holder.iv, position);
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public void setListener(CustomItemClickListener listener) {
        this.mListener = listener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }

    }
}