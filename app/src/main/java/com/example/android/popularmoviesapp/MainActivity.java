package com.example.android.popularmoviesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.android.popularmoviesapp.adapters.ImageGridAdapter;
import com.example.android.popularmoviesapp.interfaces.GetDataService;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscover;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscoverResults;
import com.example.android.popularmoviesapp.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//API KEY for TMDB c53136ae7a28a62865708255ff41f52a
// URL exemplo http://api.themoviedb.org/3/discover/movie?api_key=c53136ae7a28a62865708255ff41f52a&sort_by=popularity.desc

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<RetroTMDBDiscoverResults> mResults;
    private ImageGridAdapter mImageGridAdapter;
    private List<String> imageList;
    private ProgressBar mLoadingIndicator;
    private Spinner mOrderSpinner;
    private Context mContext;
    private EndlessRecyclerViewScrollListener scrollListener;
    private PostersViewModel viewModel;

    private String mSelectedOrder;
    private int mPage = 1;

    String[] ordens;
    HashMap<String, String> mOrders;
    int columnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ordens = getResources().getStringArray(R.array.ordens);
        mOrders = new HashMap<String, String>() {{
            put("popularity.desc", ordens[0]);
            put("vote_average.desc", ordens[1]);
            put("release_date.desc", ordens[2]);
        }};

        columnCount = getResources().getInteger(R.integer.column_count);

        mContext = this;

        initViews();

        initViewModel();
    }

    private void initViewModel() {
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this, new PostersViewModelFactory(this.getApplication(), mSelectedOrder, mPage)).get(PostersViewModel.class);
            viewModel.getPostersResponseObservable()
                    .observe(this, new Observer<RetroTMDBDiscover>() {
                        @Override
                        public void onChanged(@Nullable RetroTMDBDiscover postersResponse) {
                            if (postersResponse != null) {
                                mResults = new ArrayList<>(Arrays.asList(postersResponse.getResults()));
                                mImageGridAdapter = new ImageGridAdapter(mContext, imageList, mResults);
                                mLoadingIndicator.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(mImageGridAdapter);
                                mImageGridAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_item_grid);
        recyclerView.setHasFixedSize(true);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        mOrderSpinner = (Spinner) findViewById(R.id.sp_order);
        List<OrdensSpinner> itemList = new ArrayList<OrdensSpinner>();

        for (Map.Entry<String, String> entry : mOrders.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            itemList.add(new OrdensSpinner(value, key));
        }

        ArrayAdapter<OrdensSpinner> spinnerAdapter = new ArrayAdapter<OrdensSpinner>(this, R.layout.support_simple_spinner_dropdown_item, itemList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOrderSpinner.setAdapter(spinnerAdapter);

        mOrderSpinner.setSelection(1, false);
        OrdensSpinner selecionado = (OrdensSpinner) mOrderSpinner.getSelectedItem();
        mSelectedOrder = selecionado.getParameter();

        mOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 1- Limpar dataset
                mImageGridAdapter = null;
                OrdensSpinner selecionado = (OrdensSpinner) parentView.getSelectedItem();
                mSelectedOrder = selecionado.getParameter();
                loadDiscoverJSON();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnCount, StaggeredGridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //mPage = page+1;
                //loadDiscoverJSON();
                //viewModel.notify();
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        loadDiscoverJSON();
        //loadDiscoverJSON(2);
    }


    private void loadDiscoverJSON() {

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(mSelectedOrder, mPage);

        GetDataService request = retrofit.create(GetDataService.class);

        Call<RetroTMDBDiscover> call = request.getMoviesDiscover();
        call.enqueue(new Callback<RetroTMDBDiscover>() {
            @Override
            public void onResponse(Call<RetroTMDBDiscover> call, Response<RetroTMDBDiscover> response) {

                RetroTMDBDiscover jsonResponse = response.body();

                mResults = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                if (mImageGridAdapter == null) {
                    mImageGridAdapter = new ImageGridAdapter(mContext, imageList, mResults);
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(mImageGridAdapter);
                } else{
                    mImageGridAdapter.addResults(mResults);
                    //mImageGridAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetroTMDBDiscover> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private static class OrdensSpinner {
        public String name;
        public String parameter;

        public OrdensSpinner(String name, String parameter) {
            this.name = name;
            this.parameter = parameter;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getParameter( ){
            return parameter;
        }
    }
}

