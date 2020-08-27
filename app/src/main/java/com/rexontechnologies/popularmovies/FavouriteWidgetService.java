package com.rexontechnologies.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rexontechnologies.popularmovies.data.AppDatabase;

public class FavouriteWidgetService extends RemoteViewsService {

    private AppDatabase database;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavouriteRemoteViewFactory(this.getApplicationContext());
    }

    private class FavouriteRemoteViewFactory implements RemoteViewsFactory {
        Context mcontext;
        String isFavourite;
        private int movieId;

        FavouriteRemoteViewFactory(Context applicationContext) {
            mcontext = getApplicationContext();
            database = AppDatabase.getInstance(getApplicationContext());
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences sharedPreferences = mcontext.getSharedPreferences(DetailActivity.PREF,Context.MODE_PRIVATE);
            movieId = sharedPreferences.getInt(DetailActivity.MOVIE_ID,0);

        }


        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
