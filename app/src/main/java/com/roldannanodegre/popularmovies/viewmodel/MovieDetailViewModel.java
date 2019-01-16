package com.roldannanodegre.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.roldannanodegre.popularmovies.api.ApiConstants;
import com.roldannanodegre.popularmovies.api.TheMoviewRepository;
import com.roldannanodegre.popularmovies.database.ReviewEntity;
import com.roldannanodegre.popularmovies.database.ReviewQueryResult;
import com.roldannanodegre.popularmovies.database.VideoEntity;
import com.roldannanodegre.popularmovies.database.VideoQueryResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MovieDetailViewModel extends AndroidViewModel {

    private LiveData<List<VideoEntity>> videosObservable;
    private LiveData<List<ReviewEntity>> reviewsObservable;
    private int reviewTotalPages;
    private int reviewPage;
    private boolean isLoading;
    private long moviewId;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<VideoEntity>> getVideosObservable(long movieId) {
        if(videosObservable==null){
            videosObservable = new MutableLiveData<>();
            fetchVideos(movieId);
        }
        return videosObservable;
    }

    public LiveData<List<ReviewEntity>> getReviewsObservable(long movieId) {
        if(reviewsObservable==null){
            reviewsObservable = new MutableLiveData<>();
            fetchReviews(movieId, 1);
        }
        return reviewsObservable;
    }

    private void fetchVideos(long moviewId) {
        TheMoviewRepository.getTheMoviewApi().fetchVideos(moviewId, ApiConstants.THE_MOVIE_API_KEY).enqueue(new Callback<VideoQueryResult>() {
            @Override
            public void onResponse(@EverythingIsNonNull Call<VideoQueryResult> call, @EverythingIsNonNull Response<VideoQueryResult> response) {
                if(response.body() != null && response.body().getResults() != null && !response.body().getResults().isEmpty()){
                    ((MutableLiveData<List<VideoEntity>>) videosObservable).setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<VideoQueryResult> call, Throwable t) {
                ((MutableLiveData<List<VideoEntity>>) videosObservable).setValue(null);
            }
        });
    }

    public void fetchReviews() {
        if (reviewTotalPages >= reviewPage && !isLoading) {
            fetchReviews(moviewId, reviewPage+1);
        }
    }

    private void fetchReviews(long moviewId, final int reviewPage) {
        this.moviewId = moviewId;
        isLoading = true;
        TheMoviewRepository.getTheMoviewApi().fetchRevies(moviewId, ApiConstants.THE_MOVIE_API_KEY,
                reviewPage).enqueue(new Callback<ReviewQueryResult>() {
            @Override
            public void onResponse(Call<ReviewQueryResult> call, Response<ReviewQueryResult> response) {
                if(response.body() != null && response.body().getResults() != null && !response.body().getResults().isEmpty()){
                    MovieDetailViewModel.this.reviewPage = response.body().getPage();
                    MovieDetailViewModel.this.reviewTotalPages = response.body().getTotalPages();

                    ((MutableLiveData<List<ReviewEntity>>) reviewsObservable).setValue(response.body().getResults());
                }
                isLoading = false;
            }
            @Override
            public void onFailure(Call<ReviewQueryResult> call, Throwable t) {
                ((MutableLiveData<List<ReviewEntity>>) reviewsObservable).setValue(null);
                isLoading = false;
            }
        });
    }

}
