package com.example.cs426_final_project.models.response;

import com.example.cs426_final_project.models.data.FriendDataModel;

import org.json.JSONObject;

import java.util.List;

public class FindFriendResponse {

    public String status;
    public List<FriendDataModel> results;

}
