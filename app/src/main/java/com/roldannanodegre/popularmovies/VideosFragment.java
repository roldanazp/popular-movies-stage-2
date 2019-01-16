package com.roldannanodegre.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roldannanodegre.popularmovies.adapters.VideosAdapter;
import com.roldannanodegre.popularmovies.database.VideoEntity;
import com.roldannanodegre.popularmovies.databinding.FragmentVideosBinding;

import java.io.Serializable;
import java.util.List;


public class VideosFragment extends DialogFragment implements VideosAdapter.VideosItemListener {

    private static final String ARG_PARAM1 = "param1";

    private List<VideoEntity> mParam1;

    public VideosFragment() {
        // Required empty public constructor
    }

    public static VideosFragment newInstance(List<VideoEntity> param1) {
        VideosFragment fragment = new VideosFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (List<VideoEntity>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentVideosBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);
        binding.rvVideos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvVideos.setAdapter(new VideosAdapter(this, mParam1));
        return binding.getRoot();
    }

    @Override
    public void onVideoSelected(VideoEntity movie) {
        Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        Uri videoUrl = Uri.parse(getString(R.string.youtube_watch_url, movie.getKey()));
        videoIntent.setData(videoUrl);
        startActivity(videoIntent);
    }

}
