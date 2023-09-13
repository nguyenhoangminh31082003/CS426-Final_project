package com.example.cs426_final_project.api;

import com.example.cs426_final_project.models.data.PostDataModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedApi {
    @GET("feed")
    Call<PostDataModel> getTimelineFeeds();

}
