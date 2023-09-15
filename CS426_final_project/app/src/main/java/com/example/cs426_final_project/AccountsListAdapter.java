package com.example.cs426_final_project;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs426_final_project.api.UsersApi;
import com.example.cs426_final_project.models.response.SuggestionResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import java.util.HashMap;
import java.util.List;

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
    private Activity activity;
    private HashMap<String, ListOfAccountRows> accounts;

    public AccountsListAdapter(
            Activity activity
    ) {
        this.activity = activity;
        this.accounts = new HashMap<String, ListOfAccountRows>();
        this.accounts.put(SUGGESTIONS_HEADER, new ListOfAccountRows());
        this.accounts.put(FRIENDS_HEADER, new ListOfAccountRows());
    }

    public AccountsListAdapter(
            Activity activity,
            ListOfAccountRows suggestions,
            ListOfAccountRows friends
    ) {
        this.activity = activity;
        this.accounts = new HashMap<String, ListOfAccountRows>();
        this.accounts.put(SUGGESTIONS_HEADER, suggestions);
        this.accounts.put(FRIENDS_HEADER, friends);
    }

    public void setListOfSuggestions(
            ListOfAccountRows suggestions
    ) {
        this.accounts.put(SUGGESTIONS_HEADER, suggestions);
    }

    @Override
    public int getGroupCount() {
        System.out.println("The number of groups is " + headers.length);
        return headers.length;
    }

    @Override
    public int getChildrenCount(
            final int i
    ) {
        System.out.println("The number of children of the " + i + "-th group is " + this.accounts.get(headers[i]).getNumberOfRows());
        return this
                .accounts
                .get(headers[i])
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
        return this
                .accounts
                .get(headers[x])
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

        if (headers[i].equals(SUGGESTIONS_HEADER))
            icon.setImageResource(R.drawable.my_friends_page_light_bulb);
        else
            icon.setImageResource(R.drawable.my_friends_page_light_bulb);//temporarily

        return view;
    }

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

        final AccountRow accountRow = this.accounts.get(headers[x]).getAccountRow(y);

        TextView accountName = view.findViewById(R.id.account_name);
        TextView relationship = view.findViewById(R.id.relationship_with_account);
        ImageView accountProfilePicture = view.findViewById(R.id.account_profile_picture);
        ImageView updateIcon = view.findViewById(R.id.update_icon);
        accountName.setText(accountRow.getUsername());

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
                                Call<String> call,
                                Response<String> response) {
                            if (response.isSuccessful()) {

                                accounts.get(headers[x]).removeAccountRowWithTheGivenID(accountRow.getUserID());

                                notifyDataSetChanged();
                            } else {
                                ApiUtilityClass.Companion.debug(response);
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<String> call,
                                Throwable t
                        ) {
                            System.err.println("Can not make friend");
                        }
                    });
                }
            });
        } else {
            updateIcon.setImageResource(R.drawable.my_friends_page_unfriend_icon);
            relationship.setText("Friend");
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(
            final int x,
            final int y
    ) {
        return false;
    }
}
