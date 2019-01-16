package com.roldannanodegre.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.roldannanodegre.popularmovies.MoviesActivity;
import com.roldannanodegre.popularmovies.R;
import com.roldannanodegre.popularmovies.api.ApiConstants;
import com.roldannanodegre.popularmovies.api.TheMoviewRepository;
import com.roldannanodegre.popularmovies.database.AppDatabase;
import com.roldannanodegre.popularmovies.database.MovieEntity;
import com.roldannanodegre.popularmovies.database.QueryResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviewsViewModel extends AndroidViewModel implements Callback<QueryResult> {

    private LiveData<List<MovieEntity>> favoriteMovies;
    private LiveData<List<MovieEntity>> theMovies;

    private int currentSearchId = R.id.popularity;
    private int tempSearchId;
    private int totalPages;
    private int page;
    private boolean isLoading;

    public MoviewsViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        favoriteMovies = database.movieDao().loadAllFavoriteMovies();
    }

    public LiveData<List<MovieEntity>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public LiveData<List<MovieEntity>> getTheMovies() {
        if (theMovies == null) {
            MutableLiveData data = new MutableLiveData<>();
            data.setValue(new ArrayList<MovieEntity>());
            theMovies = data;
            fetchMovies();
        }
        return theMovies;
    }

    public void fetchMovies() {
        if (totalPages >= page && !isLoading && currentSearchId != R.id.favorite) {
            fetchMovies(currentSearchId, ++page);
        }
    }

    public void fetchMovies(int itemId, int page) {
        tempSearchId = itemId;
        switch (itemId) {
            case R.id.popularity:
                isLoading = true;
                TheMoviewRepository.getTheMoviewApi().fetchPopularMovies(ApiConstants.THE_MOVIE_API_KEY, page).enqueue(this);
                break;
            case R.id.vote_average:
                isLoading = true;
                TheMoviewRepository.getTheMoviewApi().fetchVoteAverageMovies(ApiConstants.THE_MOVIE_API_KEY, page).enqueue(this);
                break;
            case R.id.favorite:
                this.page = page;
                this.currentSearchId = itemId;
                displayFavoriteMovies();
                break;
            default:
                throw new UnsupportedOperationException("id = " + currentSearchId + " does not exist.");
        }
    }

    @Override
    public void onResponse(@NonNull Call<QueryResult> call, Response<QueryResult> response) {
        isLoading = false;

        final QueryResult queryResult = response.body();
        if (queryResult != null) {
            currentSearchId = tempSearchId;
            page = queryResult.getPage();
            if (page == 1) {
                theMovies.getValue().clear();
                ((MutableLiveData<List<MovieEntity>>) theMovies).setValue(theMovies.getValue());
            }
            totalPages = queryResult.getTotalPages();
            sync(queryResult.getResults());
            theMovies.getValue().addAll(queryResult.getResults());
            ((MutableLiveData<List<MovieEntity>>) theMovies).setValue(theMovies.getValue());
        }
    }

    private void displayFavoriteMovies() {
        theMovies.getValue().clear();
        theMovies.getValue().addAll(favoriteMovies.getValue());
        ((MutableLiveData<List<MovieEntity>>) theMovies).setValue(theMovies.getValue());
    }

    @Override
    public void onFailure(Call<QueryResult> call, Throwable t) {
        isLoading = false;
        ((MutableLiveData<List<MovieEntity>>) theMovies).setValue(theMovies.getValue());
        Toast.makeText(getApplication(), R.string.loading_problem, Toast.LENGTH_LONG).show();
    }

    public void sync() {
        if (currentSearchId == R.id.favorite) {
            displayFavoriteMovies();
        } else {
            sync(theMovies.getValue());
            ((MutableLiveData<List<MovieEntity>>) theMovies).setValue(theMovies.getValue());
        }
    }

    private void sync(List<MovieEntity> movies) {
        if (movies != null && !movies.isEmpty()) {
            List<MovieEntity> movieEntities = favoriteMovies.getValue();
            for (MovieEntity movie : movies) {
                movie.setFavorite(movieEntities.contains(movie));
            }
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public int getCurrentSearchId() {
        return currentSearchId;
    }
}
