package com.roldannanodegre.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roldannanodegre.popularmovies.adapters.ReviewAdapter;
import com.roldannanodegre.popularmovies.database.ReviewEntity;
import com.roldannanodegre.popularmovies.databinding.FragmentReviewsBinding;

import java.io.Serializable;
import java.util.List;


public class ReviewsFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";

    private List<ReviewEntity> mParam1;


    public ReviewsFragment() {
        // Required empty public constructor
    }

    public static ReviewsFragment newInstance(List<ReviewEntity> param1) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (List<ReviewEntity>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentReviewsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false);
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvReviews.setAdapter(new ReviewAdapter(mParam1));
        return binding.getRoot();
    }

}
