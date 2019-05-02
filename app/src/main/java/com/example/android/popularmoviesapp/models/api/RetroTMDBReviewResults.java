package com.example.android.popularmoviesapp.models.api;

import com.google.gson.annotations.SerializedName;

public class RetroTMDBReviewResults {
    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
