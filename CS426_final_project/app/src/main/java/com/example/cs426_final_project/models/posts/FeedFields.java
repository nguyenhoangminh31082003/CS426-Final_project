package com.example.cs426_final_project.models.posts;

import com.google.gson.annotations.SerializedName;

public class FeedFields {
    @SerializedName("user")
    public int userId = -1;
    @SerializedName("username")
    public String username = "";
    @SerializedName("title")
    public String title = "";
    @SerializedName("body")
    public String body = "";
    @SerializedName("rating")
    public int rating = 1;
    @SerializedName("image_link")
    public String imageLink = "";
    @SerializedName("create_at")
    public String createAt = "";
}
