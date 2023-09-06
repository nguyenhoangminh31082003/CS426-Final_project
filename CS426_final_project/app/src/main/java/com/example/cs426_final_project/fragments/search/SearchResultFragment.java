package com.example.cs426_final_project.fragments.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs426_final_project.R;

public class SearchResultFragment extends Fragment {

    private String searchQuery;

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(com.example.cs426_final_project.R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvNoResult = view.findViewById(R.id.tvNoResult);
        tvNoResult.setText(String.format("No result for %s", searchQuery));

    }
}