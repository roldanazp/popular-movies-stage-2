package com.roldannanodegre.popularmovies.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roldannanodegre.popularmovies.R;
import com.roldannanodegre.popularmovies.api.ImageUrl;
import com.squareup.picasso.Picasso;

public class ImageUtil {

    private ImageUtil() { }

    public static void setImage(final ImageView imageView, ImageUrl imageUrl, final String posterPath) {
        if (posterPath == null) {
            imageView.setImageBitmap(null);
            setImageProgressVisibility(imageView, false);
        } else {
            setImageProgressVisibility(imageView, true);
            loadImage(imageView, imageUrl, posterPath);
        }
    }

    private static void loadImage(final ImageView imageView, final ImageUrl imageUrl, final String posterPath) {
        Picasso.with(imageView.getContext()).load(imageUrl.getImageUrl(posterPath))
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        setImageProgressVisibility(imageView, false);
                    }

                    @Override
                    public void onError() {
                        //Other than success display broken link image
                    }
                });
    }

    private static void setImageProgressVisibility(final ImageView imageView, boolean isVisible) {
        View dounloadProgress = ((ViewGroup) imageView.getParent()).findViewById(R.id.pb_download_progress);
        dounloadProgress.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        ImageView background = ((ViewGroup) imageView.getParent()).findViewById(R.id.iv_background);
        background.setImageResource(isVisible ? R.drawable.ic_background_image : R.drawable.ic_broken_image);
    }

}
