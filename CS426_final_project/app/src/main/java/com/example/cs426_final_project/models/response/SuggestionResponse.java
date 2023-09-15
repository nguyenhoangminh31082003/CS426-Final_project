package com.example.cs426_final_project.models.response;

import com.example.cs426_final_project.models.posts.FeedFields;
import com.google.gson.annotations.SerializedName;

public class SuggestionResponse {
    @SerializedName("model")
    public String model = "";
    @SerializedName("pk")
    public int userID = -1;
    @SerializedName("fields")
    public SuggestionFields fields = null;
}
