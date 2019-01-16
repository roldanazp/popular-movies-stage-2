package com.roldannanodegre.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roldannanodegre.popularmovies.R;
import com.roldannanodegre.popularmovies.database.VideoEntity;
import com.roldannanodegre.popularmovies.databinding.VideoListItemBinding;

import java.util.List;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosHolder> {

    private final List<VideoEntity> videos;
    private final VideosAdapter.VideosItemListener videotemListener;

    public VideosAdapter(VideosAdapter.VideosItemListener videotemListener, List<VideoEntity> videos) {
        this.videotemListener = videotemListener;
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideosAdapter.VideosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        VideoListItemBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.video_list_item, viewGroup, false);
        binding.setListener(videotemListener);
        return new VideosAdapter.VideosHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosAdapter.VideosHolder viewHolder, int index) {
        viewHolder.bind(videos.get(index));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void clear() {
        this.videos.clear();
        this.notifyDataSetChanged();
    }

    class VideosHolder extends RecyclerView.ViewHolder {

        final VideoListItemBinding binding;

        VideosHolder(VideoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(VideoEntity movie) {
            binding.setVideo(movie);
        }

    }

    public interface VideosItemListener {
        void onVideoSelected(VideoEntity movie);
    }

}
