package com.example.android.popularmoviesapp.model;

public class RetroTMDBDiscoverResults {

    private Long vote_count;

    private Long id;

    private Float vote_average;

    private String title;

    private Float popularity;

    private String poster_path;

    private String original_language;

    private String backdrop_path;

    private String overview;

    private String release_date;

    public Long getVote_count() {
        return vote_count;
    }


    public Long getId() {
        return id;
    }


    public Float getVote_average() {
        return vote_average;
    }


    public String getTitle() {
        return title;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return "http://image.tmdb.org/t/p/w185" + poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

}
