package com.example.android.popularmoviesapp;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class PostersViewModelFactory implements ViewModelProvider.Factory{
    private Application mApplication;
    private String mOrder;
    private int mPage;


    public PostersViewModelFactory(Application application, String order, int page) {
        mApplication = application;
        mOrder = order;
        mPage = page;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new PostersViewModel(mApplication, mOrder, mPage);
    }
}
