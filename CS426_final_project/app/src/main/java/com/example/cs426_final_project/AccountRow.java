package com.example.cs426_final_project;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountRow {

    private String email;
    private String name;

    public AccountRow(final JSONObject record) {
        try {
            this.email = record.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.name = record.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public AccountRow(final String email, final String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public JSONObject toJSONObject() {
        JSONObject row = new JSONObject();
        try {
            row.put("email", this.email);
            row.put("name", this.name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return row;
    }

}
