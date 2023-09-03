package com.example.cs426_final_project.fragments.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.cs426_final_project.FriendsListAdapter;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.SuggestionsListAdapter;
import com.example.cs426_final_project.contracts.MainPageContract;

public class MyFriendsFragment extends MainPageFragment {

    private FriendsListAdapter friendsListAdapter;
    private SuggestionsListAdapter suggestionsListAdapter;
    private ExpandableListView friendsListView, suggestionsListView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.enableX(view);
        this.setUpFriendsList();
    }

    private void setUpFriendsList() {
        this.friendsListView = getView().findViewById(R.id.friends_list);
        this.friendsListAdapter = new FriendsListAdapter(getActivity());
        this.friendsListView.setAdapter(this.friendsListAdapter);
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