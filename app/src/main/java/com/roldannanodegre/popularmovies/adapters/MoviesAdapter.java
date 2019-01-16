package com.roldannanodegre.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roldannanodegre.popularmovies.R;
import com.roldannanodegre.popularmovies.api.ImageUrl;
import com.roldannanodegre.popularmovies.databinding.MovieListItemBinding;
import com.roldannanodegre.popularmovies.database.MovieEntity;
import com.roldannanodegre.popularmovies.util.ImageUtil;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private final List<MovieEntity> movies;
    private final MovieItemListener movieItemListener;

    public MoviesAdapter(MovieItemListener movieItemListener, List<MovieEntity> movies) {
        this.movieItemListener = movieItemListener;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MoviesAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MovieListItemBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.movie_list_item, viewGroup, false);
        binding.setSelf(binding);
        binding.setListener(movieItemListener);
        return new MovieHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder viewHolder, int index) {
        viewHolder.bind(movies.get(index));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        final MovieListItemBinding binding;

        MovieHolder(MovieListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MovieEntity movie) {
            if (movies.get(movies.size() - 1).equals(movie)) {
                movieItemListener.loadMoreMovies();
            }
            binding.setMovie(movie);

            ImageUtil.setImage(binding.ivMovieImage, ImageUrl.W342, movie.getPosterPath());
        }

    }

    public interface MovieItemListener {
        void onMovieSelected(MovieEntity movie, MovieListItemBinding binding);

        void loadMoreMovies();
    }
}
