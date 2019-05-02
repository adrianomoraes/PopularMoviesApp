package com.example.android.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

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
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public RetroTMDBVideos getVideos() {
        return videos;
    }

}
