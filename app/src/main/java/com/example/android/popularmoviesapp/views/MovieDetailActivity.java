package com.example.android.popularmoviesapp.views;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscover;
import com.example.android.popularmoviesapp.models.RetroTMDBMovieResult;
import com.example.android.popularmoviesapp.models.RetroTMDBDiscoverResults;
import com.example.android.popularmoviesapp.models.network.RetrofitClientInstance;
import com.example.android.popularmoviesapp.views.adapters.ImageGridAdapter;
import com.example.android.popularmoviesapp.views.interfaces.GetDataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailActivity extends AppCompatActivity {

    private RetroTMDBMovieResult mResult;

    ImageView iv_poster;
    ImageView iv_backdrop;
    TextView tv_title;
    TextView tv_releaseDate;
    TextView tv_plot;

    long mMovieId;

    Context mContext;
    //private ViewDataBinding mDetailBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_layout);

        iv_poster = (ImageView) findViewById(R.id.iv_poster);
        iv_backdrop = (ImageView) findViewById(R.id.iv_backdrop);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_releaseDate = (TextView) findViewById(R.id.tv_releasedate);
        tv_plot = (TextView) findViewById(R.id.tv_plot);

        mContext = this;

        mMovieId = getIntent().getLongExtra("movieId", 0);

        //tv_title.setText("teste adriano");
        viewModelUpdateDataIntoAdapter(mMovieId);

        /*CÓDIGO DO RETROFIT PARA CARGA DOS DADOS*/

        /*PICASSO PARA IMAGENS - POSTER E FUNDO*/

        /*LISTAR TRAILERS*/

            /*LANÇAR INTENT DE ABERTURA DE CADA TREILER*/

        /*CRIAR BASE PARA FAVORITOS*/
    }

    void viewModelUpdateDataIntoAdapter(long movieId){

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(movieId);

        GetDataService request = retrofit.create(GetDataService.class);

        Call<RetroTMDBMovieResult> call = request.getMoviesDetails();
        call.enqueue(new Callback<RetroTMDBMovieResult>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<RetroTMDBMovieResult> call, Response<RetroTMDBMovieResult> response) {

                RetroTMDBMovieResult jsonResponse = response.body();

                mResult = jsonResponse; //new RetroTMDBMovieResult(Arrays.asList(jsonResponse.getResults()));

                tv_title.setText(mResult.getOriginalTitle());
                tv_releaseDate.setText(mResult.getReleaseDate());
                tv_plot.setText(mResult.getOverview());

                Picasso.get()
                        .load(mResult.getPosterPath())
                        .placeholder(mContext.getDrawable(R.drawable.film_poster_placeholder))
                        //.resize(185, 277)
                        //.centerCrop()
                        .into(iv_poster);

                Picasso.get()
                        .load(mResult.getBackdropPath())
                        .placeholder(mContext.getDrawable(R.drawable.film_poster_placeholder))
                        //.resize(185, 277)
                        //.centerCrop()
                        .into(iv_backdrop);

                /*APRESENTAR DADOS RETORNADOS*/
            }

            @Override
            public void onFailure(Call<RetroTMDBMovieResult> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }

}
