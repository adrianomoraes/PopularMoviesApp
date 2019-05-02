package com.example.android.popularmoviesapp.models.dao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Favorites {

    @NonNull
    @PrimaryKey
    private long movieId;

    private String movieName;

    public Favorites() {
    }

    public long getMovieId() { return movieId; }
    public void setMovieId(long movieId) { this.movieId = movieId; }
    public String getMovieName() { return movieName; }
    public void setMovieName (String movieName) { this.movieName = movieName; }
}