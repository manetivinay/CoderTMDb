package com.vinaymaneti.codertmdb.listeners.api;

import com.vinaymaneti.codertmdb.model.NowPlaying;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vinay on 14/10/16.
 */

public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> getNowPlaying();

    //how we are gonna pass api key here
    // in retrofit we can do it in many ways
    //one way is
    //    @GET("now_playing")
    //    Call<NowPlaying> getNowPlaying(@Query("api_key") String apiKey);
    //    @Query("api_key") --> this indicates that it is going to add '?' symbol after the Base url
    //like this  "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
    //but this is a bad approach because every time we call a api we are passing api key every time
    //in feature we may have popular api
    //like this
    //    @GET("popular")
    //    Call<popular> getPopular(@Query("api_key") String apiKey);
    //we are gonna add the api key in another way, in a better manner
}
