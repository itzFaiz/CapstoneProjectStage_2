package com.rexontechnologies.popularmovies.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Movies implements Parcelable {

    private String movieId;
    private String movieName;
    private String movieImage;
    private String releaseDate;
    private String ratings;
    private String plot;
    private String isFavourite;

    protected Movies(Parcel in) {
        movieId = in.readString();
        movieName = in.readString();
        movieImage = in.readString();
        releaseDate = in.readString();
        ratings = in.readString();
        plot = in.readString();
        isFavourite = in.readString();

    }

    public Movies(){
        isFavourite = "false";
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public String getMovieId() {
        return TextUtils.isEmpty(movieId) ? "N/A" : movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return TextUtils.isEmpty(movieName) ? "N/A" : movieName;
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

    public String getReleaseDate() {
        return TextUtils.isEmpty(releaseDate) ? "N/A" : releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRatings() {
        return TextUtils.isEmpty(ratings) ? "N/A" : ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getPlot() {
        return TextUtils.isEmpty(plot) ? "N/A" : plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(movieName);
        parcel.writeString(movieImage);
        parcel.writeString(releaseDate);
        parcel.writeString(ratings);
        parcel.writeString(plot);
        parcel.writeString(isFavourite);

    }
}


