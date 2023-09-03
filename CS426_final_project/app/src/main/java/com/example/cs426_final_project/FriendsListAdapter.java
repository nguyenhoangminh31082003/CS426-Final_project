package com.example.cs426_final_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FriendsListAdapter extends BaseAdapter {

    private Activity activity;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
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
        }

        return view;
    }
}
