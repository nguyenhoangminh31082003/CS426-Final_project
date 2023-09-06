package com.example.cs426_final_project;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

public class SortModeData {

    private final static String FILE_NAME = "sort_mode_data.json";
    private final static String KEY = "mode";
    public enum Mode {
        NEAR_BY,
        BEST_RATINGS,
        POPULAR
    }

    private JSONObject record;

    public SortModeData(Activity activity) {
        this.readFile(activity);
        if (Helper.getSizeOfJSONObject(this.record) == 0) {
            System.out.println("Initialized with default value");
            try {
                this.record.put(KEY, Mode.NEAR_BY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.writeFile(activity);
        }
        System.out.println("Finish initializing");
    }

    public void readFile(Activity activity) {
        this.record = Helper.loadJSONObjectFromJSONFile(activity, SortModeData.FILE_NAME);
    }

    public void writeFile(Activity activity) {
        Helper.writeJSONObjectToJSONFile(activity, SortModeData.FILE_NAME, this.record);
    }

    public void setMode(SortModeData.Mode mode, Activity activity) {
        try {
            this.record.put(KEY, mode.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.writeFile(activity);
    }

    public String getModeAsString() {
        try {
            return this.record.getString(KEY);
        } catch (JSONException e) {
            return null;
        }
    }

    public SortModeData.Mode getMode() {
        try {
            return SortModeData.Mode.valueOf(this.record.getString(KEY));
        } catch (JSONException e) {
            return null;
        }
    }

}
