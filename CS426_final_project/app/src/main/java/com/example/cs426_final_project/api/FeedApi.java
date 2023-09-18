package com.example.cs426_final_project.api;

import com.example.cs426_final_project.models.data.PostDataModel;
import com.example.cs426_final_project.models.posts.FeedResponse;
import com.example.cs426_final_project.models.posts.TimelineFeedResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedApi {
    @GET("feed/timeline")
    Call<String> getTimelineFeeds();

    @GET("feed/timeline")
    Call<List<TimelineFeedResponse> > getTimelineWidget();

}
