package com.vinaymaneti.codertmdb.listeners.api;

import com.vinaymaneti.codertmdb.model.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vinay on 16/10/16.
 */

public interface TrailersApi {

    @GET("{id}/trailers")
    Call<Trailers> getTrailers(@Path("id") long id);
}
