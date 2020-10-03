package com.rexontechnologies.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rexontechnologies.popularmovies.data.AppDatabase;
import com.rexontechnologies.popularmovies.data.MovieEntry;
import com.rexontechnologies.popularmovies.utils.Movies;
import com.rexontechnologies.popularmovies.utils.Review;
import com.rexontechnologies.popularmovies.utils.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    public static final String PREF = "Preferences";
    public static final String MOVIE_NAME = "movieName";
    public static final String MOVIE_ID = "movieId";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    @BindView(R.id.tv_movie_name)
    TextView tv_movie_name;

    @BindView(R.id.iv_movie_cover_image)
    ImageView iv_movie_cover_image;

    @BindView(R.id.tv_movie_plot)
    TextView tv_movie_plot;

    @BindView(R.id.tv_movie_rating)
    TextView tv_movie_rating;

    @BindView(R.id.tv_movie_release)
    TextView tv_movie_release;

    @BindView(R.id.rv_list_trailer)
    RecyclerView rv_list_trailer;
    ArrayList<Trailer> trailers = new ArrayList<>();
    TrailersAdapter trailersAdapter;

    @BindView(R.id.rv_list_reviews)
    RecyclerView rv_list_reviews;

    @BindView(R.id.ib_favourite)
    ImageButton ib_favourite;

    ArrayList<Review> reviews = new ArrayList<>();
    ReviewsAdapter reviewsAdapter;

    Movies movies;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        database = AppDatabase.getInstance(getApplicationContext());

        if (intent == null) {
            closeOnError();
            return;
        }
        movies = intent.getParcelableExtra("com/rexontechnologies/popularmovies/data");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getString(getString(R.string.pref_movie_key), getString(R.string.pref_default)).equals(getString(R.string.pref_favourite_value))) {
            StringRequest request = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/" + movies.getMovieId() + "?api_key=23020eb9eddb2bb1dbbae50a8bdf63be", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        movies.setMovieId(jsonObject.optString("id", "Not Available"));
                        movies.setMovieName(jsonObject.optString("title", "Not Available"));
                        movies.setMovieImage(jsonObject.optString("poster_path", "Not Available"));
                        movies.setRatings(jsonObject.optString("vote_average", "Not Available"));
                        movies.setReleaseDate(jsonObject.optString("release_date", "Not Available"));
                        movies.setPlot(jsonObject.optString("overview", "Not Available"));
                        movies.setIsFavourite("true");
                        populateUI();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
            queue.add(request);
        }


        if (movies == null) {
            closeOnError();
            return;
        }

        fetchData(MOVIE_URL + movies.getMovieId() + "/videos?language=en-US&api_key=" + API_KEY);
        fetchData1(MOVIE_URL + movies.getMovieId() + "/reviews?api_key=" + API_KEY);
        populateUI();
    }


    private void fetchData(String url) {
        trailers.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Trailer trailer = new Trailer();
                        trailer.setId(jsonObject1.optString("id", "Not Available"));
                        trailer.setIso_639_1(jsonObject1.optString("iso_639_1", "Not Available"));
                        trailer.setIso_3166_1(jsonObject1.optString("iso_3166_1", "Not Available"));
                        trailer.setKey(jsonObject1.optString("key", "Not Available"));
                        trailer.setName(jsonObject1.optString("name", "Not Available"));
                        trailer.setSite(jsonObject1.optString("site", "Not Available"));
                        trailer.setSize(jsonObject1.optString("size", "Not Available"));
                        trailer.setType(jsonObject1.optString("type", "Not Available"));
                        trailers.add(trailer);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    rv_list_trailer.setLayoutManager(linearLayoutManager);

                    trailersAdapter = new TrailersAdapter(DetailActivity.this, trailers);
                    rv_list_trailer.setAdapter(trailersAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
        queue.add(stringRequest);
    }

    private void fetchData1(String url) {
        reviews.clear();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Review review = new Review();
                        review.setAuthor(jsonObject1.optString("author", "Not Available"));
                        review.setContent(jsonObject1.optString("content", "Not Available"));
                        review.setId(jsonObject1.optString("id", "Not Available"));
                        reviews.add(review);
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    rv_list_reviews.setLayoutManager(layoutManager);

                    reviewsAdapter = new ReviewsAdapter(DetailActivity.this, reviews);
                    rv_list_reviews.setAdapter(reviewsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
        queue.add(request);
    }

    @OnClick(R.id.ib_favourite)
    public void setIb_favourite() {
        if (movies.getIsFavourite().equals("false")) {
            final MovieEntry movieEntry = new MovieEntry(movies.getMovieId(), movies.getMovieName(), movies.getMovieImage());
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    database.taskDao().insertMovie(movieEntry);


                }
            });
            ib_favourite.setImageResource(R.drawable.ic_fav_red);
            SharedPreferences sharedPreferences = getSharedPreferences(PREF,0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MOVIE_ID,Integer.parseInt(movies.getMovieId()));
            editor.putString(MOVIE_NAME,movies.getMovieName());
            editor.commit();
            FavouriteWidget.updateWidget(this);
        } else {
            //Delete Code
            AddMovieViewModelFactory factory = new AddMovieViewModelFactory(database, movies.getMovieId());

            final AddMovieViewModel viewModel
                    = ViewModelProviders.of(this, factory).get(AddMovieViewModel.class);


            viewModel.getTask().observe(this, new Observer<MovieEntry>() {
                @Override
                public void onChanged(@Nullable MovieEntry movieEntry) {
                    viewModel.getTask().removeObserver(this);
                    database.taskDao().deleteMovie(movieEntry);
                }
            });
            ib_favourite.setImageResource(R.drawable.ic_fav_white);
        }
    }

    private void populateUI() {
        tv_movie_name.setText(movies.getMovieName());
        tv_movie_plot.setText(movies.getPlot());
        tv_movie_rating.setText(movies.getRatings());
        tv_movie_release.setText(movies.getReleaseDate());
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original" + movies.getMovieImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .noFade()
                .into(iv_movie_cover_image);
        showFavourite(movies.getIsFavourite());
    }

    private void showFavourite(String isFavourite) {
        if (isFavourite.equals("true")) {
            ib_favourite.setImageResource(R.drawable.ic_fav_red);
        } else {
            ib_favourite.setImageResource(R.drawable.ic_fav_white);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return onOptionsItemSelected(item);
    }
}