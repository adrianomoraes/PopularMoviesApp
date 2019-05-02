package com.example.android.popularmoviesapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.views.adapters.ImageGridAdapter;
import com.example.android.popularmoviesapp.models.api.RetroTMDBDiscoverResults;
import com.example.android.popularmoviesapp.views.listeners.CustomItemClickListener;
import com.example.android.popularmoviesapp.views.listeners.EndlessRecyclerViewScrollListener;
import com.example.android.popularmoviesapp.views.view_models.PostersViewModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//API KEY for TMDB c53136ae7a28a62865708255ff41f52a
// URL exemplo http://api.themoviedb.org/3/discover/movie?api_key=c53136ae7a28a62865708255ff41f52a&sort_by=popularity.desc

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<RetroTMDBDiscoverResults> mResults;
    private ImageGridAdapter mImageGridAdapter;
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

        if (savedInstanceState == null){
            loadViewModel();
        }


    }

    private void loadViewModel() {
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this, new PostersViewModelFactory(this.getApplication(), mSelectedOrder, mPage)).get(PostersViewModel.class);

            viewModelUpdateDataIntoAdapter(viewModel);

        } else {
            mPage = viewModel.getPage();
            viewModel.loadData(mSelectedOrder, mPage);

            viewModelUpdateDataIntoAdapter(viewModel);
        }

        recyclerView.scrollToPosition(viewModel.getVerticalscroll());

    }

    void viewModelUpdateDataIntoAdapter(PostersViewModel viewModel){
        viewModel.getPostersResponseObservable()
                .observe(this, new Observer<ArrayList<RetroTMDBDiscoverResults>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<RetroTMDBDiscoverResults> retroTMDBDiscoverResults) {
                        if (retroTMDBDiscoverResults != null) {
                            mResults = new ArrayList<>(retroTMDBDiscoverResults);
                            if (mImageGridAdapter == null){
                                mImageGridAdapter = new ImageGridAdapter(mContext, mResults, new CustomItemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int position) {
                                        //Log.d(TAG, "clicked position:" + position);
                                        long movieId = mResults.get(position).getIdMovie();

                                        openMovieDetails(movieId);
                                    }
                                });
                                recyclerView.setAdapter(mImageGridAdapter);
                            } else {
                                mImageGridAdapter.setResults(mResults);
                            }

                            mLoadingIndicator.setVisibility(View.INVISIBLE);
                        }
                    }
                });
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
                mImageGridAdapter = null;// .clearResults();
                OrdensSpinner selecionado = (OrdensSpinner) parentView.getSelectedItem();
                mSelectedOrder = selecionado.getParameter();
                viewModel.clearResults();
                mPage = 1;
                loadViewModel();
                recyclerView.setAdapter(mImageGridAdapter);
                //recyclerView.scrollTo(0, viewModel.getVerticalscroll());

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
                mPage++;
                viewModel.nextPage();

                loadViewModel();
                //view.scrollTo(0, viewModel.getVerticalscroll());
            }

            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                super.onScrolled(view, dx, dy);

                viewModel.addScroll(dy);
            }


        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        loadViewModel();

        recyclerView.setAdapter(mImageGridAdapter);
        //loadDiscoverJSON();


        //loadDiscoverJSON(2);
    }

    private void openMovieDetails(long movieId) {
        Intent movieDetailIntent = new Intent(this, MovieDetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putLong("movieId", movieId);
        movieDetailIntent.putExtras(bundle);

        startActivity(movieDetailIntent);
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

