package com.mikhailovskii.trakttv.ui.movies_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.model.Movie;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private List<Movie> moviesList;

    private LayoutInflater layoutInflater;

    private OnItemClickListener<Movie> onItemClickListener;

    private Context context;


    public MoviesRecyclerAdapter(List<Movie> moviesList, Context context) {
        Log.i(MoviesListFragment.TAG, "In constructor of adapter");

        this.moviesList = moviesList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.i(MoviesListFragment.TAG, "In onCreate ViewHolder");

        View itemView = layoutInflater.inflate(R.layout.movie_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.itemView.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                fireItemClicked(position, moviesList.get(position));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.i(MoviesListFragment.TAG, "In onBind ViewHolder");

        Movie movie = moviesList.get(i);
        Glide.with(context)
                .load(movie.getIconUrl())
                .into(viewHolder.mIconImageView);
        viewHolder.mMottoTextView.setText(movie.getYear());
        viewHolder.mMovieNameTextView.setText(movie.getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        Log.i(MoviesListFragment.TAG, "In getItemCount");
        return moviesList.size();
    }

    private void fireItemClicked(int position, Movie movie) {
        Log.i(MoviesListFragment.TAG, "In fire item clicked");
        if (onItemClickListener != null) {
            onItemClickListener.onItemClicked(position, movie);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<Movie> listener) {
        Log.i(MoviesListFragment.TAG, "In onClickListener");
        onItemClickListener = listener;
    }


    public interface OnItemClickListener<T> {
        void onItemClicked(int position, T item);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIconImageView;
        public TextView mMovieNameTextView;
        public TextView mMottoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(MoviesListFragment.TAG, "In ViewHolder constructor");

            mIconImageView = itemView.findViewById(R.id.icon_imageview);
            mMovieNameTextView = itemView.findViewById(R.id.moviename_textview);
            mMottoTextView = itemView.findViewById(R.id.motto_textview);

        }

    }

}
