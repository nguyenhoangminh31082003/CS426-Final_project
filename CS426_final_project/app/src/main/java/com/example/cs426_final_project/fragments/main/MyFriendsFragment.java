package com.example.cs426_final_project.fragments.main;

import static java.lang.Math.abs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.cs426_final_project.AccountsListAdapter;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.activities.SearchActivity;
import com.example.cs426_final_project.adapters.RecyclerFeedViewPagerAdapter;
import com.example.cs426_final_project.api.UsersApi;
import com.example.cs426_final_project.models.FeedInfo;
import com.example.cs426_final_project.models.data.FriendDataModel;
import com.example.cs426_final_project.models.posts.FeedFields;
import com.example.cs426_final_project.models.posts.FeedResponse;
import com.example.cs426_final_project.models.response.FindFriendResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFriendsFragment extends MainPageFragment {

    private AccountsListAdapter adapter;
    private ExpandableListView listView;

    private void findFriends() {
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(getContext())
                .create(UsersApi.class);
        Call<FindFriendResponse> call = usersApi.getFriendsOfCurrentUser();

        call.enqueue(new Callback<FindFriendResponse>() {
            @Override
            public void onResponse(
                    Call<FindFriendResponse> call,
                    Response<FindFriendResponse> response
            ) {
                if (response.isSuccessful()) {
                    FindFriendResponse body = response.body();
                    System.out.println("Successfully find friends");
                    if (body != null) {
                        //List<FriendDataModel> listOfFriends = body.results;
                        //System.out.println("The number of friends: " + listOfFriends.size());
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    Call<FindFriendResponse> call,
                    Throwable t
            ) {
                t.printStackTrace();
                System.err.println("Can not find friends :'((");
            }
        });
    }

    private void findSomeSuggestions() {
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(getContext())
                .create(UsersApi.class);
        Call<String> call = usersApi.getSomeSuggestions();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(
                    Call<String> call,
                    Response<String> response
            ) {
                if (response.isSuccessful()) {
                    String body = response.body();
                    System.out.println("Successfully find friends");
                    if (body != null) {
                        //List<FriendDataModel> listOfFriends = body.results;
                        //System.out.println("The number of friends: " + listOfFriends.size());
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    Call<String> call,
                    Throwable t
            ) {
                t.printStackTrace();
                System.err.println("Can not find friends :'((");
            }
        });
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        this.enableX(view);
        this.setUpListView();

        this.findFriends();
        this.findSomeSuggestions();
    }

    @Override
    public void onResume() {
        super.onResume();

        this.findFriends();
        this.findSomeSuggestions();
    }

    void setUpListView() {
        Activity activity = getActivity();
        this.listView = getView().findViewById(R.id.account_list_view);
        this.adapter = new AccountsListAdapter(activity);
        this.listView.setAdapter(this.adapter);

        for (int i = 0; i < this.adapter.getGroupCount(); ++i)
            this.listView.expandGroup(i);

        this.listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(final int i) {
                System.out.println("EXPAND!!!");
            }
        });

        this.listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v,
                                        final int groupPosition,
                                        final long id) {
                listView.expandGroup(groupPosition);
                return true;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
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
                getMainPageContract().setToMainPage(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void updateFragmentTransform(
            View view,
            final float position,
            final float direction,
            final float relDisplacement
    ) {
        MotionLayout rootViewFriends = view.findViewById(R.id.rootViewFriends);
        if (rootViewFriends == null)
            return;
        final float progress = 1 - abs(relDisplacement);
        rootViewFriends.setProgress(progress);
    }

}