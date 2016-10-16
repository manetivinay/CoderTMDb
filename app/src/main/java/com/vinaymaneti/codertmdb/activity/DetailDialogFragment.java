package com.vinaymaneti.codertmdb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.vinaymaneti.codertmdb.R;
import com.vinaymaneti.codertmdb.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 15/10/16.
 */

public class DetailDialogFragment extends DialogFragment {

    @BindView(R.id.landscapeModePosterIV)
    AppCompatImageView mRoundedImageView;

    @BindView(R.id.originalTitle)
    AppCompatTextView mOriginalTitle;

    @BindView(R.id.popularityTextView)
    AppCompatTextView mPopularityTextView;

    @BindView(R.id.voteCountTextView)
    AppCompatTextView mVoteCountTextView;

    @BindView(R.id.releaseDateLabel)
    AppCompatTextView vReleaseDateLabel;

    @BindView(R.id.descriptionTv)
    AppCompatTextView mDescriptionTv;

    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;


    public DetailDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DetailDialogFragment newInstance(Movie movie) {
        //this is used to pass the data
        DetailDialogFragment detailDialogFragment = new DetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        detailDialogFragment.setArguments(bundle);
        return detailDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_movie, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Movie movie = getArguments().getParcelable("movie");
        if (movie != null) {
            mOriginalTitle.setText(movie.getOriginalTitle() != null ? movie.getOriginalTitle() : "No title");
            mDescriptionTv.setText(movie.getOverview());
            mPopularityTextView.setText(String.valueOf(movie.getPopularity()));
            mVoteCountTextView.setText(String.valueOf(movie.getVoteCount()));
            vReleaseDateLabel.setText(String.valueOf(movie.getReleaseDate()));
            mRatingBar.setRating(movie.getVoteAverage() / 2);
            /*float ratingStar = movie.getVoteAverage() / 2;
            boolean flg;
            float afterRounding = Math.round(ratingStar);
            if (ratingStar - afterRounding > 0 && ratingStar - afterRounding < 0.5) {
                ratingStar = afterRounding + 0.5f;
                flg = true;
            } else {
                ratingStar = afterRounding;
                flg = false;
            }
            mRatingBar.setRating(movie.getVoteAverage() / 2);
            mRatingBar.setStepSize(movie.getVoteAverage() / 2);
            if (flg) {
                mRatingBar.setStepSize(ratingStar);
            }*/
            Glide.with(getContext())
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.horizontal_placeholder)
                    .into(mRoundedImageView);
        }
    }

}
