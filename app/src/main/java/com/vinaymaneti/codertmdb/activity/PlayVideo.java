package com.vinaymaneti.codertmdb.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vinaymaneti.codertmdb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideo extends YouTubeBaseActivity {
    public static final int RECOVERY_REQUEST = 1;

    @BindView(R.id.player)
    YouTubePlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        mPlayerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                //here we can work -- to cue video, play video etc.,
                Log.d("Enter Youtube", "Youtube");
                if (!wasRestored) {
                    youTubePlayer.loadVideo("5xVh-7ywKpE"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo  -- Example url
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
