package com.example.cs426_final_project.api;

import com.example.cs426_final_project.models.data.PostDataModel;
import com.example.cs426_final_project.models.posts.FeedResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedApi {
    @GET("feed/timeline")
    Call<ArrayList<FeedResponse> > getTimelineFeeds();

}
