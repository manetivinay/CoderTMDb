package com.vinaymaneti.codertmdb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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

    @BindView(R.id.lvMovies)
    ListView mLvMovies;

    @BindView(R.id.empty_list)
    AppCompatTextView emptyList;

    private MovieApi mMovieApi;
    private MovieAdapter mAdapter;
    List<Movie> mMovieListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMovieListView = new ArrayList<>();

        fetchData();
    }

    private void fetchData() {
        mMovieApi = RetrofitUtil.get(getString(R.string.api_key)).create(MovieApi.class);
        mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                Log.v("Response success:-", String.valueOf(response.isSuccessful()));
                if (response.body().getMovies().size() > 0) {
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
