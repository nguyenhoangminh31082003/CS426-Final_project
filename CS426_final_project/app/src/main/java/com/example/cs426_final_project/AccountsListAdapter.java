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

import java.util.HashMap;

public class AccountsListAdapter extends BaseExpandableListAdapter {

    final private static int LIMIT_NUMBER_OF_SUGGESTIONS = 5;
    final private static int LIMIT_NUMBER_OF_FRIENDS = 30;

    final private static String SUGGESTIONS_HEADER = "Suggestion";
    final private static String FRIENDS_HEADER = "Friends";
    final private static String[] headers = {SUGGESTIONS_HEADER, FRIENDS_HEADER};
    private Activity activity;
    private HashMap<String, ListOfAccountRows> accounts;

    private static ListOfAccountRows getListOfSuggestions() {
        return new ListOfAccountRows();
    }

    private static ListOfAccountRows getListOfFriends() {
        return new ListOfAccountRows();
    }

    private static HashMap<String, ListOfAccountRows> getListOfAccounts() {
        HashMap<String, ListOfAccountRows> accounts = new HashMap<String, ListOfAccountRows>();
        accounts.put(SUGGESTIONS_HEADER, getListOfSuggestions());
        accounts.put(FRIENDS_HEADER, getListOfFriends());
        return accounts;
    }


    public AccountsListAdapter(Activity activity) {
        this.activity = activity;
        this.accounts = getListOfAccounts();
    }

    @Override
    public int getGroupCount() {
        return headers.length;
    }

    @Override
    public int getChildrenCount(final int i) {
        return this.accounts.get(headers[i]).getNumberOfRows();
    }

    @Override
    public Object getGroup(final int i) {
        return headers[i];
    }

    @Override
    public Object getChild(final int x, final int y) {
        return this.accounts.get(headers[x]).getAccountRow(y);
    }

    @Override
    public long getGroupId(final int i) {
        return i;
    }

    @Override
    public long getChildId(final int x, final int y) {
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
        if (view == null) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_of_header_of_section_of_accounts, viewGroup, false);;
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
            view = inflater.inflate(R.layout.layout_of_account_row, viewGroup, false);;
        }
        final AccountRow accountRow = this.accounts.get(headers[x]).getAccountRow(y);
        TextView accountName = view.findViewById(R.id.account_name);
        TextView relationship = view.findViewById(R.id.relationship_with_account);
        ImageView accountProfilePicture = view.findViewById(R.id.account_profile_picture);
        ImageView updateIcon = view.findViewById(R.id.update_icon);

        accountName.setText(accountRow.getName());

        if (headers[y].equals(SUGGESTIONS_HEADER)) {
            updateIcon.setImageResource(R.drawable.my_friends_page_add_icon_image);
            relationship.setText("Suggestion");
        } else {
            updateIcon.setImageResource(R.drawable.my_friends_page_unfriend_icon);
            relationship.setText("Friend");
        }

        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
