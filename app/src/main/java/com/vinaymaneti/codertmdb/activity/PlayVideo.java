package com.vinaymaneti.codertmdb.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vinaymaneti.codertmdb.R;
import com.vinaymaneti.codertmdb.Util.RetrofitUtil;
import com.vinaymaneti.codertmdb.listeners.api.TrailersApi;
import com.vinaymaneti.codertmdb.model.Trailers;
import com.vinaymaneti.codertmdb.model.Youtube;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayVideo extends YouTubeBaseActivity {
    public static final int RECOVERY_REQUEST = 1;

    @BindView(R.id.player)
    YouTubePlayerView mPlayerView;

    TrailersApi mTrailersApi;
    private Youtube[] mYoutubes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id");

        mTrailersApi = RetrofitUtil.get(getString(R.string.movie_db_api_key)).create(TrailersApi.class);
        mTrailersApi.getTrailers(id).enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                Log.d("Successful play ", String.valueOf(response.body().getYoutube()));
                mYoutubes = response.body().getYoutube();
                String source = mYoutubes[0].getSource();
                playVideoBasedOnId(source);
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {
                Log.d("Error play ", t.getMessage());
            }
        });


    }

    private void playVideoBasedOnId(final String source) {
        mPlayerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                //here we can work -- to cue video, play video etc.,
                Log.d("Enter Youtube", "Youtube");
                if (!wasRestored) {
                    if (source != null)
                        youTubePlayer.loadVideo(source); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo  -- Example url
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
                Log.e("Error Youtube", "Youtube");
                if (errorReason.isUserRecoverableError()) {
                    errorReason.getErrorDialog(PlayVideo.this, RECOVERY_REQUEST);
                } else {
                    String error = String.format(getString(R.string.play_error), errorReason.toString());
                    Toast.makeText(PlayVideo.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
