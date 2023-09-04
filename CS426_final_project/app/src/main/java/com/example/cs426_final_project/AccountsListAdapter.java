package com.example.cs426_final_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountsListAdapter extends BaseAdapter {

    private Activity activity;
    private ListOfAccountRows listOfSuggestionRows, listOfFriendRows;

    public AccountsListAdapter(Activity activity) {
        this.activity = activity;
        this.listOfSuggestionRows = new ListOfAccountRows();
        this.listOfFriendRows = new ListOfAccountRows();
    }

    @Override
    public int getCount() {
        return this.listOfSuggestionRows.getNumberOfRows() + this.listOfFriendRows.getNumberOfRows();
    }

    @Override
    public Object getItem(final int i) {
        return this.listOfFriendRows.getFriendRow(i);
    }

    @Override
    public long getItemId(final int i) {
        return i;
    }

    @Override
    public View getView(
            final int i,
            View view,
            ViewGroup viewGroup
    ) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.layout_of_friend_row, null);
            TextView friendName = view.findViewById(R.id.friend_name);
            friendName.setText(this.listOfFriendRows.getFriendRow(i).getName());
        }

        return view;
    }
}
