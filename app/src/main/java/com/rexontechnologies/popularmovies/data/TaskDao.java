package com.rexontechnologies.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rexontechnologies.popularmovies.utils.Movies;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("Select * FROM favourite ORDER BY movieId")
    LiveData<List<MovieEntry>> loadAllMovies();

    @Query("Select * From  favourite ORDER BY movieId")
    List<MovieEntry> loadAllMoviesSync();

    @Insert
    void insertMovie(MovieEntry movieEntry);

    @Delete
    void deleteMovie(MovieEntry movieEntry);

    @Query("SELECT * FROM favourite WHERE movieId = :id")
    LiveData<MovieEntry> loadMovieByMovieId(String id);

    @Query("SELECT * FROM favourite WHERE movieId = :id")
    Movies getSelectedMovies(int id);
}
