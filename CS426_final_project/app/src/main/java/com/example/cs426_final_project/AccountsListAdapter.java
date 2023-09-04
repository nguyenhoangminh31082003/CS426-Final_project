package com.example.cs426_final_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
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
        accounts.put(headers[0], getListOfSuggestions());
        accounts.put(headers[1], getListOfFriends());
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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
