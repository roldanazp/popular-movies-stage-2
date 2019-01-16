package com.roldannanodegre.popularmovies.api;

import android.net.Uri;

public enum ImageUrl {

    W92("w92"), W154("w154"), W185("w185"), W342("w342"),
    W500("w500"), W780("w780"), ORIGINAL("original");

    private static final String BASE_URL = "http://image.tmdb.org/t/p";

    private final String size;

    ImageUrl(final String size) {
        this.size = size;
    }

    public String getImageUrl(String posterPath) {
        return Uri.parse(BASE_URL).buildUpon().appendPath(size)
                .appendPath(posterPath.substring(1)).build().toString();
    }

}
