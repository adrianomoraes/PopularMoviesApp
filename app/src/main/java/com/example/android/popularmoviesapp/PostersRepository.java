package com.example.android.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.view.View;

import com.example.android.popularmoviesapp.adapters.ImageGridAdapter;
import com.example.android.popularmoviesapp.interfaces.GetDataService;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscover;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscoverResults;
import com.example.android.popularmoviesapp.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostersRepository {
    private GetDataService getDataService;
    private ArrayList<RetroTMDBDiscoverResults>  mResults;

    public PostersRepository() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance("popularity.desc", 1);
        getDataService = retrofit.create(GetDataService.class);
    }

    private static class SingletonHelper {
         private static final PostersRepository INSTANCE = new PostersRepository();
    }

    public static PostersRepository getInstance()    {
        return SingletonHelper.INSTANCE;
    }

    public PostersRepository(String order, int page)    {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(order, page);
        getDataService = retrofit.create(GetDataService.class);
    }

    public LiveData<RetroTMDBDiscover> getPosters() {
        final MutableLiveData<RetroTMDBDiscover> data = new MutableLiveData<>();

        Call<RetroTMDBDiscover> call = getDataService.getMoviesDiscover();
        call.enqueue(new Callback<RetroTMDBDiscover>() {
            @Override
            public void onResponse(Call<RetroTMDBDiscover> call, Response<RetroTMDBDiscover> response) {

                if (response.isSuccessful()) {
                    RetroTMDBDiscover jsonResponse = response.body();
                }

                /*mResults = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));

                if (mImageGridAdapter == null) {
                    mImageGridAdapter = new ImageGridAdapter(mContext, imageList, mResults);
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(mImageGridAdapter);
                } else{
                    mImageGridAdapter.addResults(mResults);
                    //mImageGridAdapter.notifyDataSetChanged();
                }*/
            }

            @Override
            public void onFailure(Call<RetroTMDBDiscover> call, Throwable t) {
                Log.d("Error",t.getMessage());
                data.setValue(null);
            }
        });

   /*     getDataService.getHeadLine(country, key)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });*/


        return data;
    }


    public LiveData<RetroTMDBDiscover> getPostersPage(String order, int page) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(order, page);
        getDataService = retrofit.create(GetDataService.class);

        final MutableLiveData<RetroTMDBDiscover> data = new MutableLiveData<>();

        Call<RetroTMDBDiscover> call = getDataService.getMoviesDiscover();
        call.enqueue(new Callback<RetroTMDBDiscover>() {
            @Override
            public void onResponse(Call<RetroTMDBDiscover> call, Response<RetroTMDBDiscover> response) {

                if (response.isSuccessful()) {
                    RetroTMDBDiscover jsonResponse = response.body();
                }
            }

            @Override
            public void onFailure(Call<RetroTMDBDiscover> call, Throwable t) {
                Log.d("Error",t.getMessage());
                data.setValue(null);
            }
        });

        return data;
    }
}