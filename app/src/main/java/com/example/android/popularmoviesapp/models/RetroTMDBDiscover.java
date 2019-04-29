package com.example.android.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

public class RetroTMDBDiscover {

    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    private RetroTMDBDiscoverResults[] results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int total_results) {
        this.totalResults = total_results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int total_pages) {
        this.totalPages = total_pages;
    }

    public RetroTMDBDiscoverResults[] getResults() {
        return results;
    }

    public void setResults(RetroTMDBDiscoverResults[] results) {
        this.results = results;
    }



}
