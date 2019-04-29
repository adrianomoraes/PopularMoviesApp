package com.example.android.popularmoviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.android.popularmoviesapp.models.RetroTMDBDiscover;

public class PostersViewModel  extends AndroidViewModel {

    LiveData<RetroTMDBDiscover> postersResponseObservable;
    private String mOrder;
    private int mPage;

    private static final MutableLiveData MUTABLE_LIVE_DATA = new MutableLiveData();
    {
        MUTABLE_LIVE_DATA.setValue(null);
    }

    public final ObservableField<RetroTMDBDiscover> project = new ObservableField<>();

    public PostersViewModel(@NonNull PostersRepository postersRepository, @NonNull Application application){
        super(application);
        postersResponseObservable = PostersRepository.getInstance().getPostersPage(mOrder, mPage);
    }

    public PostersViewModel(@NonNull Application application, String order, int page){
        super(application);
        this.mOrder = order;
        this.mPage = page;
        postersResponseObservable = PostersRepository.getInstance().getPostersPage(mOrder, mPage);
    }

    public LiveData<RetroTMDBDiscover> getPostersResponseObservable()
    {
        return postersResponseObservable;
    }
}

