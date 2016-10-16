package com.vinaymaneti.codertmdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.vinaymaneti.codertmdb.R;
import com.vinaymaneti.codertmdb.activity.DetailDialogFragment;
import com.vinaymaneti.codertmdb.activity.PlayVideo;
import com.vinaymaneti.codertmdb.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 14/10/16.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    public static final float KEY_MOST_VALUED_THRESHOLD = 5.0f;

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
        ViewHolder viewHolder = null;
        BackDropViewHolder backDropViewHolder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == 0) {
                //inflate the layout with full backdrop image as the layout
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_backdrop_item, parent, false);
                backDropViewHolder = new BackDropViewHolder(convertView);
                convertView.setTag(backDropViewHolder);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.single_item_list_row, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }

        } else {
            if (type == 0)
                backDropViewHolder = (BackDropViewHolder) convertView.getTag();
            else
                viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);
        if (type == 0)
            getValuesFromMovieBackDrop(backDropViewHolder, movie, position);
        else
            getValuesFromMovieAndAssignToView(viewHolder, movie, position);

        return convertView;
    }

    private void getValuesFromMovieBackDrop(final BackDropViewHolder backDropViewHolder, Movie movie, final int position) {
        backDropViewHolder.mTitleBackdropTv.setText(movie.getOriginalTitle());
        Glide.with(getContext())
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.horizontal_placeholder)
                .into(backDropViewHolder.mFullBackdropIv);
        backDropViewHolder.mBtnMoreBackDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreButtonInfo(position);
            }
        });
        backDropViewHolder.mFullBackdropIv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                backDropViewHolder.mPlayIconIv.setVisibility(View.GONE);
                getContext().startActivity(new Intent(getContext(), PlayVideo.class));
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mMovieList.get(position).getVoteAverage() >= KEY_MOST_VALUED_THRESHOLD ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private void getValuesFromMovieAndAssignToView(ViewHolder viewHolder, final Movie movie, final int position) {
        viewHolder.tvOriginalTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverView.setText(movie.getOverview());

        Configuration configuration = getContext().getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            loadImageWithGlide(viewHolder, movie.getPosterPath(), R.drawable.vertical_placeholder);
        } else {
            loadImageWithGlide(viewHolder, movie.getBackdropPath(), R.drawable.horizontal_placeholder);
        }

        viewHolder.mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreButtonInfo(position);
            }
        });
    }

    private void moreButtonInfo(int position) {
        Movie movie = getItem(position);
        ShowDetailedDialogFragment(movie);
    }

    private void ShowDetailedDialogFragment(Movie movie) {
        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        DetailDialogFragment detailDialogFragment = DetailDialogFragment.newInstance(movie);
        detailDialogFragment.show(fragmentManager, "fragment_detail_name");
    }

    private void loadImageWithGlide(ViewHolder viewHolder, String movieOrientationPath, int placeholderIv) {
        Glide.with(getContext())
                .load(movieOrientationPath)
                .placeholder(getDrawable(placeholderIv))
                .into(viewHolder.ivPoster);
    }

    private Drawable getDrawable(int placeholderIv) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
                ContextCompat.getDrawable(getContext(), placeholderIv) :
                getContext().getResources().getDrawable(placeholderIv);
    }

    static class ViewHolder {
        @BindView(R.id.ivPoster)
        AppCompatImageView ivPoster;

        @BindView(R.id.tvOriginalTitle)
        AppCompatTextView tvOriginalTitle;

        @BindView(R.id.tvOverview)
        AppCompatTextView tvOverView;

        @BindView(R.id.btnMore)
        AppCompatButton mBtnMore;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class BackDropViewHolder {
        @BindView(R.id.fullBackdropIv)
        AppCompatImageView mFullBackdropIv;

        @BindView(R.id.playIconIv)
        AppCompatImageView mPlayIconIv;

        @BindView(R.id.titleBackdropTv)
        AppCompatTextView mTitleBackdropTv;

        @BindView(R.id.btnMoreBackDrop)
        AppCompatButton mBtnMoreBackDrop;

        BackDropViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
