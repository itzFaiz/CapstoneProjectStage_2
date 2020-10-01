package com.rexontechnologies.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.ListenableWorker.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

class MyWorker extends Worker {

    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters params) {
        super(appContext, params);
    }

    @Override
    public ListenableWorker.Result doWork() {
        // Do your work here.
        Data input = getInputData();

        // Return a ListenableWorker.Result
        Data outputData = new Data.Builder()
                .putString("Key", "value")
                .build();
        return Result.success(outputData);
    }

    @Override
    public void onStopped() {
        // Cleanup because you are being stopped.
    }
}