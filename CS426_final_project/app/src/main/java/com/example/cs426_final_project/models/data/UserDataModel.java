package com.example.cs426_final_project.models.data;

import com.google.gson.annotations.SerializedName;

public class UserDataModel {
    @SerializedName("full_name")
    public String full_name;
    @SerializedName("bio")
    public String bio;
    @SerializedName("avatar")
    public String avatar;
}
