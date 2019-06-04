package com.mikhailovskii.trakttv.ui.movie_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity
        implements MovieDetailContract.MovieDetailView {

    private MovieDetailPresenter mPresenter = new MovieDetailPresenter();
    private TextView mDescriptionTextView;
    private ImageView mMovieImageView;
    private FloatingActionButton mFloatingActionButton;
    private String mSlugId;
    private String mUrl;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mPresenter.attachView(this);

        mSlugId = getIntent().getStringExtra(MovieListFragment.EXTRA_SLUG);
        mUrl = getIntent().getStringExtra(MovieListFragment.EXTRA_IMAGE);
        mTitle = getIntent().getStringExtra(MovieListFragment.EXTRA_MOVIE);

        Objects.requireNonNull(getSupportActionBar()).setTitle(mTitle);

        mDescriptionTextView = findViewById(R.id.description_textview);
        mMovieImageView = findViewById(R.id.movie_image);
        mFloatingActionButton = findViewById(R.id.favorite_button);
        mFloatingActionButton.setOnClickListener(view -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show());

        if (mSlugId != null) {
            mPresenter.loadMovieDetails(mSlugId);
        }
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {
        Toast.makeText(this, "Empty details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
    }

    @Override
    public void onMovieDetailsLoaded(@NonNull Movie movie) {
        // mDescriptionTextView.setText(movie);

        Glide.with(this).load(mUrl).into(mMovieImageView);
    }

    @Override
    public void onMovieDetailsFailed() {

    }

}
