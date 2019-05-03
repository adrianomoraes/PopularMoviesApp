package com.example.android.popularmoviesapp.models.api;

import com.google.gson.annotations.SerializedName;

public class RetroTMDBVideoResults {

    //youtube URLs https://www.youtube.com/watch?v=      k3kzqVliF48   (key)

    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getYoutubeUrl() {
        return "https://www.youtube.com/watch?v=" + key;
    }
}
