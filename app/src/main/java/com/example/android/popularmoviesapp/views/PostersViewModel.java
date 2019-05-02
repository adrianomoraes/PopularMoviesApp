package com.example.android.popularmoviesapp.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmoviesapp.views.interfaces.GetDataService;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscover;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscoverResults;
import com.example.android.popularmoviesapp.models.network.RetrofitClientInstance;
import com.example.android.popularmoviesapp.views.view_models.PostersRepository;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostersViewModel  extends AndroidViewModel {

    MutableLiveData<ArrayList<RetroTMDBDiscoverResults>> postersResponseObservable;
    private ArrayList<RetroTMDBDiscoverResults> mResults;
    private String mOrder = "popularity.desc";;
    private int mPage = 1;
    private int verticalscroll = 0;

    private static final MutableLiveData MUTABLE_LIVE_DATA = new MutableLiveData();
    {
        MUTABLE_LIVE_DATA.setValue(null);
    }

    public final ObservableField<RetroTMDBDiscover> project = new ObservableField<>();


    public PostersViewModel(@NonNull Application application, String order, int page){
        super(application);
        this.mOrder = order;
        this.mPage = page;
        postersResponseObservable = PostersRepository.getInstance().getPostersPage(mOrder, mPage);
    }

    public MutableLiveData<ArrayList<RetroTMDBDiscoverResults>> getPostersResponseObservable()
    {
        return postersResponseObservable;
    }

    void clearResults(){
        mResults.clear();
        mPage = 1;
    }

    void nextPage(){
        mPage++;
    }

    public int getPage() {
        return mPage;
    }

    void addScroll (int dy){
        verticalscroll = verticalscroll + dy;
    }

    public int getVerticalscroll() {
        return verticalscroll;
    }

    public ArrayList<RetroTMDBDiscoverResults> getmResults() {
        return mResults;
    }

    public void loadData(String mOrder, int mPage) {
        // do async operation to fetch and use postValue() method
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(mOrder, mPage);

        GetDataService request = retrofit.create(GetDataService.class);

        Call<RetroTMDBDiscover> call = request.getMoviesDiscover();
        call.enqueue(new Callback<RetroTMDBDiscover>() {
            @Override
            public void onResponse(Call<RetroTMDBDiscover> call, Response<RetroTMDBDiscover> response) {

                RetroTMDBDiscover jsonResponse = response.body();

                if (mResults == null){
                    mResults = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                }else{
                    ArrayList<RetroTMDBDiscoverResults> newResults = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                    mResults.addAll(newResults);
                }

                postersResponseObservable.postValue(mResults);

            }

            @Override
            public void onFailure(Call<RetroTMDBDiscover> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });


    }
}

