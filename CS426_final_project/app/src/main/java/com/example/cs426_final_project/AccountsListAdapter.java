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
        System.out.println("The number of groups is " + headers.length);
        return headers.length;
    }

    @Override
    public int getChildrenCount(final int i) {
        System.out.println("The number of children of the " + i + "-th group is " + this.accounts.get(headers[i]).getNumberOfRows());
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
        System.out.println("Get children view " + x + ", " + y);
        if (view == null) {
            System.err.println("1");
            Context context = viewGroup.getContext();
            System.err.println("2");
            LayoutInflater inflater = LayoutInflater.from(context);
            System.err.println("3");
            view = inflater.inflate(R.layout.layout_of_account_row, null);
            System.err.println("4");
        }
        System.err.println("5");
        final AccountRow accountRow = this.accounts.get(headers[x]).getAccountRow(y);
        System.err.println("6");
        TextView accountName = view.findViewById(R.id.account_name);
        System.err.println("7");
        TextView relationship = view.findViewById(R.id.relationship_with_account);
        System.err.println("8");
        ImageView accountProfilePicture = view.findViewById(R.id.account_profile_picture);
        System.err.println("9");
        ImageView updateIcon = view.findViewById(R.id.update_icon);
        System.err.println("10");
        accountName.setText(accountRow.getName());
        System.err.println("11");
        if (headers[x].equals(SUGGESTIONS_HEADER)) {
            System.err.println("11.1a");
            updateIcon.setImageResource(R.drawable.my_friends_page_add_icon_image);
            System.err.println("11.1b");
            relationship.setText("Suggestion");
            System.err.println("11.1c");
        } else {
            System.err.println("11.2a");
            updateIcon.setImageResource(R.drawable.my_friends_page_unfriend_icon);
            System.err.println("11.2b");
            relationship.setText("Friend");
            System.err.println("11.2c");
        }
        System.err.println("12");
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
