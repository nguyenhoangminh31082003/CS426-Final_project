package com.example.cs426_final_project;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListOfAccountRows {

    private JSONObject records;
    private ArrayList<Integer> listOfUserIDs;

    private ArrayList<Integer> findAllUserIDs() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<String> key = this.records.keys();

        while (key.hasNext())
            result.add(Integer.valueOf(key.next()));

        return result;
    }

    public ListOfAccountRows() {
        this.records = new JSONObject();
        this.listOfUserIDs = new ArrayList<Integer>();
    }

    public int getNumberOfRows() {
        return this.listOfUserIDs.size();
    }

    public AccountRow getAccountRow(final int position) {
        try {
            return new AccountRow(this.records.getJSONObject(String.valueOf(this.listOfUserIDs.get(position))));
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean addAccountRow(AccountRow row) {
        final String key = String.valueOf(row.getUserID());
        final boolean result = !this.records.has(key);

        try {
            this.records.put(key, row.toJSONObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (result)
            this.listOfUserIDs.add(row.getUserID());

        return result;
    }

    public boolean removeAccountRowWithTheGivenID(final int id) {
        final int size = this.listOfUserIDs.size();
        final Integer value = Integer.valueOf(id);
        for (int i = 0; i < size; ++i)
            if (this.listOfUserIDs.get(i).equals(value)) {
                this.records.remove(String.valueOf(id));
                this.listOfUserIDs.remove(i);
                return true;
            }
        return false;
    }

}
