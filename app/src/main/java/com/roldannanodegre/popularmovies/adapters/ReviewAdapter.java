package com.roldannanodegre.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roldannanodegre.popularmovies.R;
import com.roldannanodegre.popularmovies.database.ReviewEntity;
import com.roldannanodegre.popularmovies.databinding.ReviewListItemBinding;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private final List<ReviewEntity> videos;

    public ReviewAdapter(List<ReviewEntity> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ReviewListItemBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.review_list_item, viewGroup, false);
        return new ReviewAdapter.ReviewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewHolder viewHolder, int index) {
        viewHolder.bind(videos.get(index));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {

        final ReviewListItemBinding binding;

        ReviewHolder(ReviewListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ReviewEntity movie) {
            binding.setReview(movie);
        }

    }

}
