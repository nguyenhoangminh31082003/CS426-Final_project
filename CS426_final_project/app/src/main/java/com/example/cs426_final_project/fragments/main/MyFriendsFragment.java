package com.example.cs426_final_project.fragments.main;

import static java.lang.Math.abs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.cs426_final_project.FriendsListAdapter;
import com.example.cs426_final_project.R;

public class MyFriendsFragment extends MainPageFragment {

    private FriendsListAdapter friendsListAdapter;
//    private SuggestionsListAdapter suggestionsListAdapter;
    private ListView friendsListView, suggestionsListView;

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
        ImageButton button = view.findViewById(R.id.btnFriendsClose);

        button.setOnClickListener(v -> {
            mainPageContract.setToMainPage();
        });
    }

    public static void updateFragmentTransform(View view, float position, float direction, float relDisplacement) {
        MotionLayout rootViewFriends = view.findViewById(R.id.rootViewFriends);
        if(rootViewFriends == null) return;
        float progress = 1 - abs(relDisplacement);
        rootViewFriends.setProgress(progress);
    }

}