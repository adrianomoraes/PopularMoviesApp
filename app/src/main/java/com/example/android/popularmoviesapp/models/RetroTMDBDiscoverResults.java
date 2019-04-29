package com.example.android.popularmoviesapp.models;

import com.example.android.popularmoviesapp.R;
import com.google.gson.annotations.SerializedName;

public class RetroTMDBDiscoverResults {
    @SerializedName("vote_count")
    private Long voteCount;
    @SerializedName("id")
    private Long idMovie;
    @SerializedName("vote_average")
    private Float voteAverage;

    private String title;

    private Float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("backdrop_path")
    private String backdropPath;

    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public Long getVoteCount() {
        return voteCount;
    }


    public Long getIdMovie() {
        return idMovie;
    }


    public Float getVoteAverage() {
        return voteAverage;
    }


    public String getTitle() {
        return title;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return "http://image.tmdb.org/t/p/w185" + posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
