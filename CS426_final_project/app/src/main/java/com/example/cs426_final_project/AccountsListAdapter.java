package com.example.cs426_final_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cs426_final_project.api.UsersApi;
import com.example.cs426_final_project.models.data.UserDataModel;
import com.example.cs426_final_project.utilities.ImageUtilityClass;
import com.example.cs426_final_project.utilities.ApiUtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsListAdapter extends BaseExpandableListAdapter {
    final private static int LIMIT_NUMBER_OF_SUGGESTIONS = 5;
    final private static int LIMIT_NUMBER_OF_FRIENDS = 30;

    final private static String SUGGESTIONS_HEADER = "Suggestions";
    final private static String FRIENDS_HEADER = "Friends";
    final private static String[] headers = {
            SUGGESTIONS_HEADER,
            FRIENDS_HEADER
    };
    private final Activity activity;
    private final HashMap<String, ListOfAccountRows> accounts;
    private HashMap<String, List<Boolean>> initialized;

    private List<Boolean> getListOfBoolean(final int length) {
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < length; ++i)
            result.add(false);
        return result;
    }
    private void setUpInitializedHashMap() {
        this.initialized = new HashMap<String, List<Boolean> >();
        this.initialized.put(FRIENDS_HEADER, this.getListOfBoolean(this.accounts.get(FRIENDS_HEADER).getNumberOfRows()));
        this.initialized.put(SUGGESTIONS_HEADER, this.getListOfBoolean(this.accounts.get(SUGGESTIONS_HEADER).getNumberOfRows()));
    }

    public AccountsListAdapter(
            Activity activity
    ) {
        this.activity = activity;
        this.accounts = new HashMap<>();
        this.accounts.put(SUGGESTIONS_HEADER, new ListOfAccountRows());
        this.accounts.put(FRIENDS_HEADER, new ListOfAccountRows());

        this.setUpInitializedHashMap();
    }

    public AccountsListAdapter(
            Activity activity,
            ListOfAccountRows suggestions,
            ListOfAccountRows friends
    ) {
        this.activity = activity;
        this.accounts = new HashMap<>();
        this.accounts.put(SUGGESTIONS_HEADER, suggestions);
        this.accounts.put(FRIENDS_HEADER, friends);
        this.setUpInitializedHashMap();
    }

    public void setListOfSuggestions(
            ListOfAccountRows suggestions
    ) {
        this.accounts.put(SUGGESTIONS_HEADER, suggestions);
        this.initialized.put(SUGGESTIONS_HEADER, this.getListOfBoolean(suggestions.getNumberOfRows()));
    }

    public void setListOfFriends(
            ListOfAccountRows friends
    ) {
        this.accounts.put(FRIENDS_HEADER, friends);
        this.initialized.put(FRIENDS_HEADER, this.getListOfBoolean(friends.getNumberOfRows()));
    }

    @Override
    public int getGroupCount() {
        return headers.length;
    }

    @Override
    public int getChildrenCount(
            final int i
    ) {
        return Objects.requireNonNull(this
                        .accounts
                        .get(headers[i]))
                .getNumberOfRows();
    }

    @Override
    public Object getGroup(
            final int i
    ) {
        return headers[i];
    }

    @Override
    public Object getChild(
            final int x,
            final int y
    ) {
        return Objects.requireNonNull(this
                        .accounts
                        .get(headers[x]))
                .getAccountRow(y);
    }

    @Override
    public long getGroupId(
            final int i
    ) {
        return i;
    }

    @Override
    public long getChildId(
            final int x,
            final int y) {
        return y;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i,
                             final boolean isExpanded,
                             View view,
                             ViewGroup viewGroup) {
        System.out.println("Get group view " + i);
        if (view == null) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_of_header_of_section_of_accounts, null);;
        }
        TextView title = view.findViewById(R.id.title_of_section_of_accounts);
        ImageView icon = view.findViewById(R.id.icon_of_section_of_accounts);

        title.setText(headers[i]);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        if (headers[i].equals(SUGGESTIONS_HEADER))
            icon.setImageResource(R.drawable.my_friends_page_light_bulb);
        else
            icon.setImageResource(R.drawable.my_friends_page_light_bulb);//temporarily

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getChildView(final int x,
                             final int y,
                             final boolean isExpanded,
                             View view,
                             ViewGroup viewGroup) {
        if (view == null) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_of_account_row, null);
        }

        final AccountRow accountRow = Objects.requireNonNull(this.accounts.get(headers[x])).getAccountRow(y);

        TextView accountName = view.findViewById(R.id.account_name);
        TextView relationship = view.findViewById(R.id.relationship_with_account);
        ImageView accountProfilePicture = view.findViewById(R.id.account_profile_picture);
        ImageView updateIcon = view.findViewById(R.id.update_icon);
        accountName.setText(accountRow.getUsername());

        if (!Objects.requireNonNull(this.initialized.get(headers[x])).get(y)) {
            Objects.requireNonNull(this.initialized.get(headers[x])).set(y, true);
            this.setUserProfilePicture(
                    accountProfilePicture,
                    accountRow.getUserID()
            );
        }

        if (headers[x].equals(SUGGESTIONS_HEADER)) {
            updateIcon.setImageResource(R.drawable.my_friends_page_add_icon_image);
            relationship.setText("Suggestion");
            updateIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UsersApi usersApi = ApiUtilityClass
                            .Companion
                            .getApiClient(activity)
                            .create(UsersApi.class);
                    Call<String> call = usersApi.changeFriend(accountRow.getUserID());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<String> call,
                                @NonNull Response<String> response) {
                            if (response.isSuccessful()) {
                                Objects.requireNonNull(accounts.get(FRIENDS_HEADER)).addAccountRow(accountRow);
                                Objects.requireNonNull(accounts.get(SUGGESTIONS_HEADER)).removeAccountRowWithTheGivenID(accountRow.getUserID());
                                Objects.requireNonNull(initialized.get(FRIENDS_HEADER)).add(true);
                                Objects.requireNonNull(initialized.get(SUGGESTIONS_HEADER)).remove(y);
                                notifyDataSetChanged();
                            } else {
                                ApiUtilityClass.Companion.debug(response);
                            }
                        }

                        @Override
                        public void onFailure(
                                @NonNull Call<String> call,
                                @NonNull Throwable t
                        ) {
                            System.err.println("Can not make friend");
                        }
                    });
                }
            });
        } else {
            updateIcon.setImageResource(R.drawable.my_friends_page_unfriend_icon);
            relationship.setText("Friend");

            updateIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UsersApi usersApi = ApiUtilityClass
                            .Companion
                            .getApiClient(activity)
                            .create(UsersApi.class);
                    Call<String> call = usersApi.changeFriend(accountRow.getUserID());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<String> call,
                                @NonNull Response<String> response) {
                            if (response.isSuccessful()) {
                                Objects.requireNonNull(accounts.get(FRIENDS_HEADER)).removeAccountRowWithTheGivenID(accountRow.getUserID());
                                Objects.requireNonNull(initialized.get(FRIENDS_HEADER)).remove(y);
                                notifyDataSetChanged();
                            } else {
                                ApiUtilityClass.Companion.debug(response);
                            }
                        }

                        @Override
                        public void onFailure(
                                @NonNull Call<String> call,
                                @NonNull Throwable t
                        ) {
                            System.err.println("Can not make friend");
                        }
                    });
                }
            });
        }

        return view;
    }

    private void setUserProfilePicture(
            ImageView view,
            final int id
    ) {
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(activity)
                .create(UsersApi.class);
        Call<UserDataModel> call = usersApi.getUser(id);

        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(
                    @NonNull Call<UserDataModel> call,
                    @NonNull Response<UserDataModel> response
            ) {
                if (response.isSuccessful()) {
                    final UserDataModel data = response.body();
                    assert data != null;
                    final String link = data.avatar;
                    if (link.startsWith("http")) {
                        ImageUtilityClass
                                .Companion
                                .loadSquareImageViewFromUrl(
                                        view,
                                        link,
                                        60,
                                        8
                                );
                    } else {
                        Uri uri = Uri.parse(link);
                        view.setImageURI(uri);
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<UserDataModel> call,
                    @NonNull Throwable t
            ) {
                System.err.println("Error in getting user data");
            }
        });
    }

    @Override
    public boolean isChildSelectable(
            final int x,
            final int y
    ) {
        return false;
    }
}
