package com.roldannanodegre.popularmovies.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMoviewRepository {

    private TheMoviewRepository() { }

    private static TheMovieAPI theMoviewApi;

    public static TheMovieAPI getTheMoviewApi() {
        if (theMoviewApi == null) {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            theMoviewApi = retrofit.create(TheMovieAPI.class);
        }
        return theMoviewApi;
    }
}
