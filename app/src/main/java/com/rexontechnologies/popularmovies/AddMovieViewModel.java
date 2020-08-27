package com.rexontechnologies.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rexontechnologies.popularmovies.data.AppDatabase;
import com.rexontechnologies.popularmovies.data.MovieEntry;


public class AddMovieViewModel extends ViewModel {


    private LiveData<MovieEntry> task;


    public AddMovieViewModel(AppDatabase database, String movieId) {
        task = database.taskDao().loadMovieByMovieId(movieId);
    }

    public LiveData<MovieEntry> getTask() {
        return task;
    }
}
