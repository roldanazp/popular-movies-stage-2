package com.roldannanodegre.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.roldannanodegre.popularmovies.api.ImageUrl;
import com.roldannanodegre.popularmovies.database.AppDatabase;
import com.roldannanodegre.popularmovies.database.MovieEntity;
import com.roldannanodegre.popularmovies.database.ReviewEntity;
import com.roldannanodegre.popularmovies.database.VideoEntity;
import com.roldannanodegre.popularmovies.databinding.ActivityMovieDetailBinding;
import com.roldannanodegre.popularmovies.util.AppExecutors;
import com.roldannanodegre.popularmovies.util.ImageUtil;
import com.roldannanodegre.popularmovies.viewmodel.MovieDetailViewModel;

import java.util.List;

@BindingMethods({
        @BindingMethod(type = android.widget.ImageView.class,
                attribute = "app:srcCompat",
                method = "setImageDrawable")})
public class MovieDetailActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private ActivityMovieDetailBinding binding;
    private MovieEntity movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        movie = (MovieEntity) getIntent().getSerializableExtra(MovieEntity.KEY);

        initUi();
        setupViewModel();

    }

    private void initUi() {
        binding.imageLayout.ivMovieImage.setTransitionName(String.valueOf(movie.getId()));
        binding.setMovie(movie);
        binding.setListener(new MovieInteractionListener() {
            @Override
            public void onFavoriteClick(final MovieEntity movie) {
                movie.setFavorite(!movie.isFavorite());
                binding.setMovie(movie);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (movie.isFavorite()) {
                            mDb.movieDao().insertMovie(movie);
                        } else {
                            mDb.movieDao().deleteMovie(movie);
                        }
                    }
                });
            }

            @Override
            public void onVideosClick(List videos) {
                VideosFragment.newInstance(videos).show(getSupportFragmentManager(), VideosFragment.class.getCanonicalName());
            }

            @Override
            public void onReviewsClick(List reviews) {
                ReviewsFragment.newInstance(reviews).show(getSupportFragmentManager(), ReviewsFragment.class.getCanonicalName());
            }
        });

        ImageUtil.setImage(binding.imageLayout.ivMovieImage, ImageUrl.W342, movie.getPosterPath());
    }

    private void setupViewModel() {
        MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        viewModel.getVideosObservable(movie.getId()).observe(this, new Observer<List<VideoEntity>>() {
            @Override
            public void onChanged(@Nullable List<VideoEntity> movieEntities) {
                binding.setVideos(movieEntities);
            }
        });
        viewModel.getReviewsObservable(movie.getId()).observe(this, new Observer<List<ReviewEntity>>() {
            @Override
            public void onChanged(@Nullable List<ReviewEntity> reviewEntities) {
                binding.setReviews(reviewEntities);
            }
        });
    }

    public interface MovieInteractionListener {
        void onFavoriteClick(MovieEntity movie);

        void onVideosClick(List videos);

        void onReviewsClick(List videos);
    }

}
