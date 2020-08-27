package com.rexontechnologies.popularmovies.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favourite")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieId;
    private String movieName;
    private String movieImage;


    @Ignore
    public MovieEntry(String movieName, String movieImage){
        this.movieName = movieName;
        this.movieImage = movieImage;
    }
//    public MovieEntry(int id,String movieId, String movieTitle, String movieImage) {
//        this.id = id;
//        this.movieId = movieId;
//        this.movieName = movieName;
//        this.movieImage = movieImage;
//    }

    public MovieEntry(String movieId, String movieName, String movieImage) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieImage = movieImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

}
