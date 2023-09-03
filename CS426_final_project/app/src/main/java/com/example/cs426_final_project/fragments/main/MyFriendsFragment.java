package com.example.cs426_final_project.fragments.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cs426_final_project.R;

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
                R.layout.fragment_my_friends,
                container,
                false);
    }


    private void enableX(View view) {
        ImageView button = view.findViewById(R.id.x_button);

        button.setOnClickListener(v -> {
            mainPageContract.setToMainPage();
        });
    }


    public static void updateFragmentTransform(View view, float position, float direction, float relDisplacement) {
        View rootViewFriends = view.findViewById(R.id.rootViewFriends);
        if(rootViewFriends == null){
            return;
        }
        // print out the position, direction, and relative displacement
        System.out.println("Friends: position: " + position + ", direction: " + direction + ", relDisplacement: " + relDisplacement);
    }


}