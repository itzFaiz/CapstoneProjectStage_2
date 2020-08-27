package com.rexontechnologies.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rexontechnologies.popularmovies.data.AppDatabase;


public class AddMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final AppDatabase mDb;
    private final String movieId;


    public AddMovieViewModelFactory(AppDatabase database, String movieId) {
        mDb = database;
        this.movieId = movieId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AddMovieViewModel(mDb, movieId);
    }
}
