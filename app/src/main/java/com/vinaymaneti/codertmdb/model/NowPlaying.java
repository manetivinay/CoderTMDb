package com.vinaymaneti.codertmdb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinay on 14/10/16.
 */

public class NowPlaying {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "NowPlaying{" +
                "movies=" + movies +
                '}';
    }
}
