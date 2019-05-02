package com.example.android.popularmoviesapp.views.interfaces;

import com.example.android.popularmoviesapp.models.RetroTMDBDiscover;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/3/discover/movie")
    Call<RetroTMDBDiscover> getMoviesDiscover();


}
