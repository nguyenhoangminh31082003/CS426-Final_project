package com.example.cs426_final_project.api

import com.example.cs426_final_project.models.data.PostDataModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface FeedApi {

    @GET("feed/timeline")
    fun getFeeds(): Call<PostDataModel>

}