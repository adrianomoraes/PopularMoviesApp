package com.example.android.popularmoviesapp.models.api;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RetroTMDBMovieResult {


    @SerializedName("id")
    private Long idMovie;

    @SerializedName("vote_average")
    private Float voteAverage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    private String overview;

    @SerializedName("videos")
    private RetroTMDBVideos videos;

    @SerializedName("reviews")
    private RetroTMDBReviews reviews;

    public RetroTMDBReviews getReviews() {
        return reviews;
    }

    @SerializedName("backdrop_path")
    String backdropPath;

    public String getBackdropPath() {
        return "http://image.tmdb.org/t/p/w185" + backdropPath;
    }

    public Long getIdMovie() {
        return idMovie;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return "http://image.tmdb.org/t/p/w185" + posterPath;
    }

    public String getReleaseDate() {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
        Date date = null;
        try {
            date = (Date)formatter.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        String finalString = newFormat.format(date);

        return finalString;
    }

    public String getOverview() {
        return overview;
    }

    public RetroTMDBVideos getVideos() {
        return videos;
    }

}
