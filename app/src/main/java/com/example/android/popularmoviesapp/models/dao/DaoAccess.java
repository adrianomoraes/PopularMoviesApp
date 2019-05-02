package com.example.android.popularmoviesapp.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleMovie (Favorites favorite);

    @Insert
    void insertMultipleMovies (List<Favorites> favoritesList);

    @Query("SELECT * FROM Favorites WHERE movieId = :movieId")
    Favorites fetchOneMoviesbyMovieId (long movieId);

    @Update
    void updateMovie (Favorites favorites);

    @Delete
    void deleteMovie (Favorites favorites);
}