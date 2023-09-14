package com.example.cs426_final_project.models.posts;

import com.google.gson.annotations.SerializedName;

public class FeedResponse {
    @SerializedName("model")
    public String model;
    @SerializedName("pk")
    public int id;
    @SerializedName("fields")
    public FeedFields fields = null;
}
