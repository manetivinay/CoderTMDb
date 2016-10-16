package com.vinaymaneti.codertmdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinay on 14/10/16.
 */

public class NowPlaying implements Parcelable{
    @SerializedName("results")
    private List<Movie> movies;

    protected NowPlaying(Parcel in) {
        movies = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<NowPlaying> CREATOR = new Creator<NowPlaying>() {
        @Override
        public NowPlaying createFromParcel(Parcel in) {
            return new NowPlaying(in);
        }

        @Override
        public NowPlaying[] newArray(int size) {
            return new NowPlaying[size];
        }
    };

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "NowPlaying{" +
                "movies=" + movies +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(movies);
    }
}
