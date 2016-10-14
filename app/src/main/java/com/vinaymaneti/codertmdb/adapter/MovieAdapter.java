package com.vinaymaneti.codertmdb.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.vinaymaneti.codertmdb.R;
import com.vinaymaneti.codertmdb.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 14/10/16.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    List<Movie> mMovieList;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, -1);
        this.mMovieList = movies;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return mMovieList.get(position);
    }

    @NonNull
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.single_item_list_row, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);

        getValuesFromMovieAndAssignToView(viewHolder, movie);

        return convertView;
    }

    private void getValuesFromMovieAndAssignToView(ViewHolder viewHolder, Movie movie) {
        viewHolder.tvOriginalTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverView.setText(movie.getOverview());
        Configuration configuration = getContext().getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            loadImageWithGlide(viewHolder, movie.getPosterPath());
        } else {
            loadImageWithGlide(viewHolder, movie.getBackdropPath());
        }
    }

    private void loadImageWithGlide(ViewHolder viewHolder, String movieOrientationPath) {
        Glide.with(getContext())
                .load(movieOrientationPath)
                .into(viewHolder.ivPoster);
    }

    static class ViewHolder {
        @BindView(R.id.ivPoster)
        AppCompatImageView ivPoster;

        @BindView(R.id.tvOriginalTitle)
        AppCompatTextView tvOriginalTitle;

        @BindView(R.id.tvOverview)
        AppCompatTextView tvOverView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
