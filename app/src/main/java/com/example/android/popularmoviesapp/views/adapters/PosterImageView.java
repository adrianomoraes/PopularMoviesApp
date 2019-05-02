package com.example.android.popularmoviesapp.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.example.android.popularmoviesapp.views.MovieDetailActivity;


public class PosterImageView extends AppCompatImageView {
    public PosterImageView(Context context) {
        super(context);
    }

    public PosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PosterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }
}