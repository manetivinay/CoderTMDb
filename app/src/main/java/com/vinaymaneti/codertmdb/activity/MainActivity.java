package com.vinaymaneti.codertmdb.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vinaymaneti.codertmdb.R;
import com.vinaymaneti.codertmdb.Util.RetrofitUtil;
import com.vinaymaneti.codertmdb.adapter.MovieAdapter;
import com.vinaymaneti.codertmdb.listeners.api.MovieApi;
import com.vinaymaneti.codertmdb.model.Movie;
import com.vinaymaneti.codertmdb.model.NowPlaying;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_MOVIE_OBJECT = "movie_key";

    @BindView(R.id.lvMovies)
    ListView mLvMovies;

    @BindView(R.id.empty_list)
    AppCompatTextView emptyList;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.swipeContainers)
    SwipeRefreshLayout swipeContainer;

    private MovieApi mMovieApi;
    private MovieAdapter mAdapter;
    List<Movie> mMovieListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMovieListView = new ArrayList<>();
        Log.d("onCreate ::-", "onCreate");

        if (savedInstanceState != null)
            mMovieListView = savedInstanceState.getParcelableArrayList(KEY_MOVIE_OBJECT);

        swipeToRefresh();
        fetchData();
    }

    private void swipeToRefresh() {
        //setup refresh listener which  triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // here we need to refresh the list here
                // we need to make sure to call swipeContainer.setRefreshing(false)
                //once the network request has completed successfully.


                //send the network request to fetch the updated data
                mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
                    @Override
                    public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                        if (response != null)
                            // here first we need to CLEAR OUT old items before appending new items
                            mAdapter.clear();
                        mMovieListView.clear();
                        //once after the data come back from server successfully we need to add new
                        mMovieListView = response.body().getMovies();
                        mAdapter.addAll(mMovieListView);
                        mAdapter = new MovieAdapter(MainActivity.this, mMovieListView);
                        mLvMovies.setAdapter(mAdapter);
                        // now her we can call setRefreshing(false) to signal refresh has finished
                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<NowPlaying> call, Throwable t) {
                        Log.e("on swipe refresh :-", t.getMessage());
                    }
                });
            }
        });

        //configuration the refreshing color
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save custom values into the bundle
        if (outState != null) {
            outState.putParcelableArrayList(KEY_MOVIE_OBJECT, (ArrayList<? extends Parcelable>) mMovieListView);
        }
        //Always call teh superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
        Log.d("onSaveInstanceState ::-", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // we need to always call superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mMovieListView = savedInstanceState.getParcelableArrayList(KEY_MOVIE_OBJECT);
            Log.d("onRestore ::-", "onRestoreInstanceState");
        }
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        mMovieApi = RetrofitUtil.get(getString(R.string.movie_db_api_key)).create(MovieApi.class);
        mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                Log.v("Response success:-", String.valueOf(response.isSuccessful()));
                if (response.body().getMovies().size() > 0) {
                    progressBar.setVisibility(View.GONE);
                    mMovieListView = response.body().getMovies();
                    mAdapter = new MovieAdapter(MainActivity.this, mMovieListView);
                    mLvMovies.setAdapter(mAdapter);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    emptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}
