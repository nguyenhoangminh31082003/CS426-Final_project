package com.example.cs426_final_project;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListOfAccountRows {

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
        AccountRow[] rows = {
                new AccountRow("viserys.targaryen.63@gmail.com", "Viserys Targaryen"),
                new AccountRow("daemon.targaryen.81@gmail.com", "Daemon Targaryen")
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

    public ListOfAccountRows() {
        this.records = new JSONObject();
        this.friendsEmails = new ArrayList<String>();

        this.testByAddingSomeFriends();
    }

    public int getNumberOfRows() {
        return this.friendsEmails.size();
    }

    public AccountRow getAccountRow(final int position) {
        try {
            return new AccountRow(this.records.getJSONObject(this.friendsEmails.get(position)));
        } catch (JSONException e) {
            return null;
        }
    }

}
