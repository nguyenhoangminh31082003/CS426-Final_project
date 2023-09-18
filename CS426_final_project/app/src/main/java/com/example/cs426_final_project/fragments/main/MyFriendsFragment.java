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

import com.example.cs426_final_project.AccountRow;
import com.example.cs426_final_project.AccountsListAdapter;
import com.example.cs426_final_project.ListOfAccountRows;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.api.UsersApi;
import com.example.cs426_final_project.models.response.FriendsResponse;
import com.example.cs426_final_project.models.response.SuggestionFields;
import com.example.cs426_final_project.models.response.SuggestionResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFriendsFragment extends MainPageFragment {

    private AccountsListAdapter adapter;
    private ExpandableListView listView;

    private void findFriends() {
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(requireContext())
                .create(UsersApi.class);
        Call<List<FriendsResponse> > call = usersApi.getFriendsOfCurrentUser();

        call.enqueue(new Callback<List<FriendsResponse>  >() {
            @Override
            public void onResponse(
                    @NonNull Call<List<FriendsResponse>  > call,
                    @NonNull Response<List<FriendsResponse>  > response
            ) {
                if (response.isSuccessful()) {
                    List<FriendsResponse>  body = response.body();
                    System.out.println("Successfully find friends");
                    if (body != null) {
                        System.out.println("The number of friends: " + body.size());
                        final int size = body.size();
                        ListOfAccountRows rows = new ListOfAccountRows();
                        for (int i = 0; i < size; ++i) {
                            rows.addAccountRow(new AccountRow(
                                    body.get(i).id,
                                    body.get(i).username
                            ));
                        }
                        adapter.setListOfFriends(rows);
                        listView.setAdapter(adapter);
                        expandListView();
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<List<FriendsResponse>  > call,
                    @NonNull Throwable t
            ) {
                t.printStackTrace();
                System.err.println("Can not find friends :'((");
            }
        });
    }

    private void findSomeSuggestions() {
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(requireContext())
                .create(UsersApi.class);
        Call<List<SuggestionResponse> > call = usersApi.getSomeSuggestions();

        call.enqueue(new Callback<List<SuggestionResponse> >() {
            @Override
            public void onResponse(
                    @NonNull Call<List<SuggestionResponse> > call,
                    @NonNull Response<List<SuggestionResponse> > response
            ) {
                if (response.isSuccessful()) {
                    List<SuggestionResponse> body = response.body();
                    System.out.println("Successfully find suggestions");
                    if (body != null) {
                        final int size = body.size();
                        ListOfAccountRows rows = new ListOfAccountRows();
                        for (int i = 0; i < size; ++i) {
                            SuggestionFields fields = body.get(i).fields;
                            rows.addAccountRow(new AccountRow(
                                    body.get(i).userID,
                                    fields.getUsername()
                            ));
                        }
                        adapter.setListOfSuggestions(rows);
                        listView.setAdapter(adapter);
                        expandListView();
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<List<SuggestionResponse>> call,
                    @NonNull Throwable t
            ) {
                t.printStackTrace();
                System.err.println("Can not find any suggestions");
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

    private void expandListView() {
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

    private void setUpListView() {
        Activity activity = getActivity();
        this.listView = requireView().findViewById(R.id.account_list_view);
        this.adapter = new AccountsListAdapter(activity);
        this.listView.setAdapter(this.adapter);
        this.expandListView();
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
            final float ignoredPosition,
            final float ignoredDirection,
            final float relDisplacement
    ) {
        MotionLayout rootViewFriends = view.findViewById(R.id.rootViewFriends);
        if (rootViewFriends == null)
            return;
        final float progress = 1 - abs(relDisplacement);
        rootViewFriends.setProgress(progress);
    }

}