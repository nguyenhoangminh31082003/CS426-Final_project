package com.example.cs426_final_project;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

public class SortModeData {

    private final static String FILE_NAME = "search_mode_data.json";
    private final String KEY = "mode";
    public final String NEAR_BY_MODE = "near_by";
    public final String BEST_RATINGS_MODE = "best_ratings";
    public final String POPULAR_MODE = "popular";

    private JSONObject record;

    public SortModeData(Activity activity) {
        this.readFile(activity);
        if (Helper.getSizeOfJSONObject(this.record) == 0) {
            try {
                this.record.put(KEY, NEAR_BY_MODE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.writeFile(activity);
        }
    }

    public void readFile(Activity activity) {
        this.record = Helper.loadJSONObjectFromJSONFile(activity, SortModeData.FILE_NAME);
    }

    public void writeFile(Activity activity) {
        Helper.writeJSONObjectToJSONFile(activity, SortModeData.FILE_NAME, this.record);
    }

    public void setMode() {

    }

}
