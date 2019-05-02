package com.example.android.popularmoviesapp.views.view_models;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.popularmoviesapp.views.interfaces.GetDataService;
import com.example.android.popularmoviesapp.models.api.RetroTMDBDiscover;
import com.example.android.popularmoviesapp.models.api.RetroTMDBDiscoverResults;
import com.example.android.popularmoviesapp.models.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;

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


    public MutableLiveData<ArrayList<RetroTMDBDiscoverResults>> getPostersPage(String mOrder, int mPage) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(mOrder, mPage);
        getDataService = retrofit.create(GetDataService.class);

        final MutableLiveData<ArrayList<RetroTMDBDiscoverResults>> data = new MutableLiveData<>();

        Call<RetroTMDBDiscover> call = getDataService.getMoviesDiscover();
        call.enqueue(new Callback<RetroTMDBDiscover>() {
            @Override
            public void onResponse(Call<RetroTMDBDiscover> call, Response<RetroTMDBDiscover> response) {

                if (response.isSuccessful()) {
                    RetroTMDBDiscover jsonResponse = response.body();
                    mResults = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));

                    data.setValue(mResults);
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