package com.example.android.popularmoviesapp.interfaces;

import com.example.android.popularmoviesapp.model.RetroTMDBDiscover;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/3/discover/movie?api_key=c53136ae7a28a62865708255ff41f52a&sort_by=popularity.desc")
    Call<RetroTMDBDiscover> getMoviesDiscover();


}
