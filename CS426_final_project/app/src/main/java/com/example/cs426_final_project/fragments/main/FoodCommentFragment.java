package com.example.cs426_final_project.fragments.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs426_final_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodCommentFragment extends Fragment {

    public FoodCommentFragment() {
        // Required empty public constructor
    }

    public static FoodCommentFragment newInstance() {
        FoodCommentFragment fragment = new FoodCommentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_comment, container, false);
    }
}