package com.example.cs426_final_project;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountRow {

    private int userID;
    private String username;

    public AccountRow(
            final JSONObject record
    ) {
        try {
            this.userID = record.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.username = record.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public AccountRow(
            final int userID,
            final String username
    ) {
        this.userID = userID;
        this.username = username;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getUsername() {
        return this.username;
    }

    public JSONObject toJSONObject() {
        JSONObject row = new JSONObject();
        try {
            row.put("user_id", this.userID);
            row.put("username", this.username);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return row;
    }

}
