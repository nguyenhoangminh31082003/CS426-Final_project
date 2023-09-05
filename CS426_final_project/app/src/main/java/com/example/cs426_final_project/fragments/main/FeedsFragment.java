package com.example.cs426_final_project.fragments.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.activities.SearchActivity;

public class FeedsFragment extends Fragment {

    public interface OnFeedsFragmentListener {
        void onRequestToScanFood();
    }

    public OnFeedsFragmentListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton ibToScan = view.findViewById(R.id.ibToScan);
        ibToScan.setOnClickListener(v -> {
            if (listener != null)
                listener.onRequestToScanFood();
        });

        ImageButton ibFeedFoodSearch = view.findViewById(R.id.ibFeedFoodSearch);
        ibFeedFoodSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
    }
}