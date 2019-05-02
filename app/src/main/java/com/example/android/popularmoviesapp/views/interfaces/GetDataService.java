package com.example.android.popularmoviesapp.views.interfaces;

import com.example.android.popularmoviesapp.models.api.RetroTMDBDiscover;
import com.example.android.popularmoviesapp.models.api.RetroTMDBMovieResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/3/discover/movie")
    Call<RetroTMDBDiscover> getMoviesDiscover();

    @GET("/3/movie")
    Call<RetroTMDBMovieResult> getMoviesDetails();
}
