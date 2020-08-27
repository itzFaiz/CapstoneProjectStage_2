package com.rexontechnologies.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rexontechnologies.popularmovies.data.AppDatabase;
import com.rexontechnologies.popularmovies.data.MovieEntry;
import com.rexontechnologies.popularmovies.utils.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv_error_message_display)
    TextView tv_error_message_display;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar pb_loading_indicator;

    ArrayList<Movies> arrayList = new ArrayList<>();
    MoviesAdapter moviesAdapter;
    Movies movies;

//    private static final String TAG = "MainActivity";

    private AdView mAdView;

    private static final String MOST_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private static final String TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final int MOVIES_LOADER_ID = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mDb = AppDatabase.getInstance(getApplicationContext());
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(gridLayoutManager);
        moviesAdapter = new MoviesAdapter(MainActivity.this, this);
        rv_list.setAdapter(moviesAdapter);
        if (isOnline()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            loadData(sharedPreferences.getString(getString(R.string.pref_movie_key),
                    getString(R.string.pref_default)));
        } else {
            Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    void fetchData(String url) {
        arrayList.clear();
        pb_loading_indicator.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < 20; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Movies movies = new Movies();
                        movies.setMovieId(jsonObject1.optString("id", "Not Available"));
                        movies.setMovieName(jsonObject1.optString("title", "Not Available"));
                        movies.setMovieImage(jsonObject1.optString("poster_path", "Not Available"));
                        movies.setRatings(jsonObject1.optString("vote_average", "Not Available"));
                        movies.setReleaseDate(jsonObject1.optString("release_date", "Not Available"));
                        movies.setPlot(jsonObject1.optString("overview", "Not Available"));

                        arrayList.add(movies);
                    }
                    if (moviesAdapter != null) {
                        showMoviesDataView();
                        moviesAdapter.setMovieData(arrayList);
                    } else {
                        showErrorMessage();
                    }
                    pb_loading_indicator.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    pb_loading_indicator.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pb_loading_indicator.setVisibility(View.INVISIBLE);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
    }

    private void showMoviesDataView() {
        tv_error_message_display.setVisibility(View.INVISIBLE);
        rv_list.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rv_list.setVisibility(View.INVISIBLE);

        tv_error_message_display.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(final int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        final LiveData<MovieEntry> task = mDb.taskDao().loadMovieByMovieId(arrayList.get(clickedItemIndex).getMovieId());
        // COMPLETED (4) Observe tasks and move the logic from runOnUiThread to onChanged
        task.observe(this, new Observer<MovieEntry>() {
            @Override
            public void onChanged(@Nullable MovieEntry taskEntry) {
                // COMPLETED (5) Remove the observer as we do not need it any more
                task.removeObserver(this);
                Log.d(TAG, "Receiving database update from LiveData");
                if (task == null) {
                    arrayList.get(clickedItemIndex).setIsFavourite("false");
                }else{
                    arrayList.get(clickedItemIndex).setIsFavourite("true");
                }
            }
        });
        intent.putExtra("com/rexontechnologies/popularmovies/data", arrayList.get(clickedItemIndex));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    private void invalidateData() {
        moviesAdapter.setMovieData(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;

            /**
             return true;**/
        }
        return super.onOptionsItemSelected(item);
    }

    void loadData(String key) {
        if (key.equals(getString(R.string.pref_most_popular_value))) {
            if (isOnline()) {

                fetchData(MOST_POPULAR_URL + API_KEY);
            } else {
                Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            }

        } else if (key.equals(getString(R.string.pref_top_rated_value))) {
            if (isOnline()) {

                fetchData(TOP_RATED_URL + API_KEY);
            } else {
                Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            }
        } else if (key.equals(getString(R.string.pref_default))) {
            if (isOnline()) {
                fetchData(MOST_POPULAR_URL + API_KEY);
            } else {
                Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            }
        } else if (key.equals(getString(R.string.pref_favourite_value))) {
            arrayList.clear();
            pb_loading_indicator.setVisibility(View.VISIBLE);
            setupViewModel();
        }
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> taskEntries) {
                Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
                for (MovieEntry m1 : taskEntries
                ) {
                    Movies movies = new Movies();
                    movies.setMovieId(m1.getMovieId());
                    movies.setMovieName(m1.getMovieName());
                    movies.setMovieImage(m1.getMovieImage());
                    arrayList.add(movies);
                }
                if (moviesAdapter != null) {
                    showMoviesDataView();
                    moviesAdapter.setMovieData(arrayList);
                } else {
                    showErrorMessage();
                }
                pb_loading_indicator.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_movie_key))) {
            loadData(sharedPreferences.getString(getString(R.string.pref_movie_key),
                    getString(R.string.pref_default)));
        }
    }
}