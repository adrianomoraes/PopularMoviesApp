package com.example.android.popularmoviesapp.views;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.models.api.RetroTMDBMovieResult;
import com.example.android.popularmoviesapp.models.api.RetroTMDBReviewResults;
import com.example.android.popularmoviesapp.models.api.RetroTMDBReviews;
import com.example.android.popularmoviesapp.models.dao.Favorites;
import com.example.android.popularmoviesapp.models.dao.FavoritesDatabase;
import com.example.android.popularmoviesapp.models.network.RetrofitClientInstance;
import com.example.android.popularmoviesapp.views.interfaces.GetDataService;
import com.squareup.picasso.Picasso;

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
    TextView tv_reviews;
    ToggleButton toggleFavoritos;

    long mMovieId;

    Context mContext;

    private static final String DATABASE_NAME = "favorites_db";
    private FavoritesDatabase movieDatabase;
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
        tv_reviews = (TextView) findViewById(R.id.tv_reviews);

        toggleFavoritos = (ToggleButton) findViewById(R.id.toggleFavoritos);
        toggleFavoritos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                favoriteOnOff(toggleFavoritos.isChecked());
            }
        });

        mContext = this;

        mMovieId = getIntent().getLongExtra("movieId", 0);

        movieDatabase = Room.databaseBuilder(getApplicationContext(),
                FavoritesDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();


        viewModelUpdateDataIntoAdapter(mMovieId);



        /*LISTAR TRAILERS*/

            /*LANÇAR INTENT DE ABERTURA DE CADA TREILER*/

        /*CRIAR BASE PARA FAVORITOS*/
    }

    private void favoriteOnOff(final boolean state){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Favorites movie = new Favorites();
                movie.setMovieId(mMovieId);
                movie.setMovieName((String) tv_title.getText());

                if (state) {
                     if(!movieDatabase.daoAccess().fetchOneMoviesbyMovieId(mMovieId).equals(null)) {
                        movieDatabase.daoAccess().insertOnlySingleMovie(movie);
                    }
                }else {
                     movieDatabase.daoAccess().deleteMovie(movie);
                }

            }
        }) .start();
    }


    void viewModelUpdateDataIntoAdapter(final long movieId){

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

                toggleFavoritos.setChecked(isFavorite(mResult.getIdMovie()));

                String reviewContent = "";

                if (mResult.getReviews()!= null){
                    RetroTMDBReviews reviews =  mResult.getReviews();

                    for(RetroTMDBReviewResults review :  reviews.getResults()){
                        reviewContent = reviewContent + "Author: " + review.getAuthor() + "\n";
                        reviewContent = reviewContent + review.getContent() + "\n________________\n\n";

                    }
                } else{
                    reviewContent = "No reviews posted at this time.";
                }

                tv_reviews.setText(reviewContent);

            }

            public boolean isFavorite(long movieId){
                return !movieDatabase.daoAccess().fetchOneMoviesbyMovieId(mMovieId).equals(null);
            }

            @Override
            public void onFailure(Call<RetroTMDBMovieResult> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }

}
