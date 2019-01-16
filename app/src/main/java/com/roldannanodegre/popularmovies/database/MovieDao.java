package com.roldannanodegre.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<MovieEntity>> loadAllFavoriteMovies();

    @Insert
    void insertMovie(MovieEntity taskEntry);

    @Delete
    void deleteMovie(MovieEntity taskEntry);

}
