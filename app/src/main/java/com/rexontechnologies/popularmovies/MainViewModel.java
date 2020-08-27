package com.rexontechnologies.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.rexontechnologies.popularmovies.data.AppDatabase;
import com.rexontechnologies.popularmovies.data.MovieEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<MovieEntry>> tasks;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        tasks = database.taskDao().loadAllMovies();
    }

    public LiveData<List<MovieEntry>> getTasks() {
        return tasks;
    }
}
