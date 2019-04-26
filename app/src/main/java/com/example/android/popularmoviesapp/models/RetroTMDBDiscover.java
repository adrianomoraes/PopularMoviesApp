package com.example.android.popularmoviesapp.models;

public class RetroTMDBDiscover {

    private int page;

    private int total_results;

    private int total_pages;

    private RetroTMDBDiscoverResults[] results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public RetroTMDBDiscoverResults[] getResults() {
        return results;
    }

    public void setResults(RetroTMDBDiscoverResults[] results) {
        this.results = results;
    }



}
