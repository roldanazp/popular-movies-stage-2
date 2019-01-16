package com.roldannanodegre.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roldannanodegre.popularmovies.adapters.MoviesAdapter;
import com.roldannanodegre.popularmovies.adapters.MoviesAdapter.MovieItemListener;
import com.roldannanodegre.popularmovies.database.MovieEntity;
import com.roldannanodegre.popularmovies.databinding.ActivityMainBinding;
import com.roldannanodegre.popularmovies.databinding.MovieListItemBinding;
import com.roldannanodegre.popularmovies.viewmodel.MoviewsViewModel;

import java.util.List;

public class MoviesActivity extends AppCompatActivity implements MovieItemListener {

    private static final int SPAN_COUNT = 3;

    private MoviesAdapter moviesAdapter;

    private MoviewsViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        setupViewModel();
    }

    private void initUi() {
        setTitle(getString(R.string.movies_by, getString(R.string.search_popularity)));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.rvMovies.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MoviewsViewModel.class);
        viewModel.getFavoriteMovies().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MovieEntity> favoriteMovieEntities) {
                viewModel.sync();
            }
        });
        viewModel.getTheMovies().observe(MoviesActivity.this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> movieEntities) {
                binding.pbMovies.setVisibility(viewModel.isLoading() ? View.VISIBLE : View.GONE);
                if (moviesAdapter == null) {
                    moviesAdapter = new MoviesAdapter(MoviesActivity.this, movieEntities);
                    binding.rvMovies.setAdapter(moviesAdapter);
                }
                moviesAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (viewModel.getCurrentSearchId() != item.getItemId()) {
            binding.pbMovies.setVisibility(View.VISIBLE);
            setTitle(getString(R.string.movies_by, item.getTitle()));
            binding.rvMovies.smoothScrollToPosition(0);
            viewModel.fetchMovies(item.getItemId(), 1);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSelected(final MovieEntity movie, MovieListItemBinding binding) {
        Intent movieDetailIntent = new Intent(this, MovieDetailActivity.class);
        movieDetailIntent.putExtra(MovieEntity.KEY, movie);
        binding.ivMovieImage.setTransitionName(String.valueOf(movie.getId()));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, binding.ivMovieImage, String.valueOf(movie.getId()));
        startActivity(movieDetailIntent, options.toBundle());
    }

    @Override
    public void loadMoreMovies() {
        viewModel.fetchMovies();
    }

}
