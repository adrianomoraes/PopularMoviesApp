package com.example.android.popularmoviesapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.android.popularmoviesapp.interfaces.GetDataService;
import com.example.android.popularmoviesapp.model.RetroTMDBDiscover;
import com.example.android.popularmoviesapp.model.RetroTMDBDiscoverResults;
import com.example.android.popularmoviesapp.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//API KEY for TMDB c53136ae7a28a62865708255ff41f52a
// URL exemplo http://api.themoviedb.org/3/discover/movie?api_key=c53136ae7a28a62865708255ff41f52a&sort_by=popularity.desc

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<RetroTMDBDiscoverResults> mResults;
    private ImageGridAdapter mIimageGridAdapter;
    private List<String> imageList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageList = new ArrayList<>();
        imageList.add("http://image.tmdb.org/t/p/w185/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
        imageList.add("http://image.tmdb.org/t/p/w185/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
        imageList.add("http://image.tmdb.org/t/p/w185/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
        imageList.add("http://image.tmdb.org/t/p/w185/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
        imageList.add("http://image.tmdb.org/t/p/w185/or06FN3Dka5tukK1e9sl16pB3iy.jpg");
        imageList.add("http://image.tmdb.org/t/p/w185/or06FN3Dka5tukK1e9sl16pB3iy.jpg");


        //mIimageGridAdapter = new ImageGridAdapter(this, imageList);
        //recyclerView.setAdapter(mIimageGridAdapter);

        mContext = this;

        initViews();

    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_item_grid);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, StaggeredGridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        loadJSON();
    }


    private void loadJSON() {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gist.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        GetDataService request = retrofit.create(GetDataService.class);

        Call<RetroTMDBDiscover> call = request.getMoviesDiscover();
        call.enqueue(new Callback<RetroTMDBDiscover>() {
            @Override
            public void onResponse(Call<RetroTMDBDiscover> call, Response<RetroTMDBDiscover> response) {

                RetroTMDBDiscover jsonResponse = response.body();

                mResults = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                mIimageGridAdapter = new ImageGridAdapter(mContext, imageList, mResults);
                recyclerView.setAdapter(mIimageGridAdapter);
            }

            @Override
            public void onFailure(Call<RetroTMDBDiscover> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}

