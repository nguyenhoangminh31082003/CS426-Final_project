package com.example.cs426_final_project.fragments.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.contracts.MainPageContract;

public class MyFriendsFragment extends MainPageFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.enableX(view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.activity_my_friends,
                container,
                false);
    }


    private void enableX(View view) {
        ImageView button = view.findViewById(R.id.x_button);

        button.setOnClickListener(v -> {
            mainPageContract.setToMainPage();
        });
    }

}