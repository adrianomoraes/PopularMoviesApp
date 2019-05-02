package com.example.android.popularmoviesapp.models.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Favorites.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase  extends RoomDatabase {
    public abstract DaoAccess daoAccess() ;
}
