package com.example.cs426_final_project;

import com.example.cs426_final_project.utilities.FriendRow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOfFriendRows {

    private JSONObject records;
    private ArrayList<String> friendsEmails;

    private ArrayList<String> findAllEmails() {
        ArrayList<String> result = new ArrayList<String>();
        Iterator<String> key = this.records.keys();

        while (key.hasNext())
            result.add(key.next());

        return result;
    }
    private void testByAddingSomeFriends() {
        FriendRow[] rows = {
                new FriendRow("viserys.targaryen.63@gmail.com", "Viserys Targaryen"),
                new FriendRow("daemon.targaryen.81@gmail.com", "Daemon Targaryen")
        };
        for (int i = 0; i < rows.length; ++i) {
            try {
                records.put(rows[i].getEmail(), rows[i].toJSONObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.friendsEmails = this.findAllEmails();
    }

    public ListOfFriendRows() {
        this.records = new JSONObject();
        this.friendsEmails = new ArrayList<String>();

        this.testByAddingSomeFriends();
    }

    public int getNumberOfRows() {
        return this.friendsEmails.size();
    }

    public FriendRow getFriendRow(final int position) {
        try {
            return new FriendRow(this.records.getJSONObject(this.friendsEmails.get(position)));
        } catch (JSONException e) {
            return null;
        }
    }

}
