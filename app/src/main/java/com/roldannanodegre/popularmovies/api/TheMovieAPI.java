package com.roldannanodegre.popularmovies.api;

import com.roldannanodegre.popularmovies.database.QueryResult;
import com.roldannanodegre.popularmovies.database.ReviewQueryResult;
import com.roldannanodegre.popularmovies.database.VideoQueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.roldannanodegre.popularmovies.api.ApiConstants.*;

public interface TheMovieAPI {

    @GET(POPULAR_MOVIE_PATH)
    Call<QueryResult> fetchPopularMovies(@Query(API_KEY) String apiKey,
                                         @Query(PAGE) int page);

    @GET(VOTE_AVERAGE_MOVIE_PATH)
    Call<QueryResult> fetchVoteAverageMovies(@Query(API_KEY) String apiKey,
                                             @Query(PAGE) int page);

    @GET(VIDEOS_MOVIE_PATH)
    Call<VideoQueryResult> fetchVideos(@Path(value = "id") long movieId, @Query(API_KEY) String apiKey);

    @GET(REVIEWS_MOVIE_PATH)
    Call<ReviewQueryResult> fetchRevies(@Path(value = "id") long movieId,
                                        @Query(API_KEY) String apiKey,
                                        @Query(PAGE) int page);
}
