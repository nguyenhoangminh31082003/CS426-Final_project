package com.example.cs426_final_project.models.data;

import com.google.gson.annotations.SerializedName;

import kotlinx.serialization.Serializable;

public class FriendDataModel {

    @SerializedName("user_from")
    public int userFrom;

    @SerializedName("user_to")
    public int userTo;

}
