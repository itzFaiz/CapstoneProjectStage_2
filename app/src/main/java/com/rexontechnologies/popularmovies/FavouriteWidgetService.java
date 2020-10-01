package com.rexontechnologies.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rexontechnologies.popularmovies.data.AppDatabase;
import com.rexontechnologies.popularmovies.utils.Movies;

import java.util.List;

public class FavouriteWidgetService extends RemoteViewsService {

    private AppDatabase database;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavouriteRemoteViewFactory(this.getApplicationContext());
    }

    private class FavouriteRemoteViewFactory implements RemoteViewsFactory {
        Context mContext;
        private int movieId;
        List<Movies>  moviesList;

        FavouriteRemoteViewFactory(Context applicationContext) {
            mContext = getApplicationContext();
            database = AppDatabase.getInstance(getApplicationContext());
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(DetailActivity.PREF,Context.MODE_PRIVATE);
            movieId = sharedPreferences.getInt(DetailActivity.MOVIE_ID,0);

            if (movieId != 0){
                moviesList = database.taskDao().getSelectedMovies(movieId).getIsFavourite();
                Log.d("", "onDataSetChanged: "+ moviesList.toString());
            }

        }


        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return moviesList == null ? 0 : moviesList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(),R.layout.list_item_widget);
            Movies movies = moviesList.get(i);
            String movieName = String.valueOf(movies.getMovieName());
            String isFavourite = movies.getIsFavourite();
            views.setTextViewText(R.id.appwidget_favorite,isFavourite + " " + movieName);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
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
