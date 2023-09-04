package com.example.cs426_final_project.fragments.main;

import static java.lang.Math.abs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.cs426_final_project.AccountsListAdapter;
import com.example.cs426_final_project.R;

import java.util.Objects;

public class MyFriendsFragment extends MainPageFragment {

    private AccountsListAdapter adapter;
    private ExpandableListView listView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.enableX(view);
        this.setUpListView();
    }

    void setUpListView() {
        Activity activity = getActivity();
        this.listView = activity.findViewById(R.id.account_list_view);
        this.adapter = new AccountsListAdapter(activity);
        this.listView.setAdapter(this.adapter);
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
            try {
                if(getMainPageContract() == null) {
                    throw new Exception("MainPageContract is null");
                }
                getMainPageContract().setToMainPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void updateFragmentTransform(View view, float position, float direction, float relDisplacement) {
        MotionLayout rootViewFriends = view.findViewById(R.id.rootViewFriends);
        if(rootViewFriends == null) return;
        float progress = 1 - abs(relDisplacement);
        rootViewFriends.setProgress(progress);
    }

}