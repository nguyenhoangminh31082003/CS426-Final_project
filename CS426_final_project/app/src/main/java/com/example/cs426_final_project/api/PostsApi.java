package com.example.cs426_final_project.api;

import com.example.cs426_final_project.models.posts.CreatePostRequest;
import com.example.cs426_final_project.models.posts.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostsApi {

    @POST("posts/")
    Call<StatusResponse> createPost(
        @Body CreatePostRequest request
    );

}

/*
*
* package com.example.cs426_final_project.api


import com.example.cs426_final_project.models.posts.CreatePostResponse
import com.example.cs426_final_project.models.posts.ReactPostResponse
import com.example.cs426_final_project.models.posts.UpdatePostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostsApi {

    @POST("posts")
    fun createPost(
        @Part image: MultipartBody.Part,
        @Part("rating") rating: RequestBody,
        @Part("image_name") imageName: RequestBody
    ): Call<CreatePostResponse>

//    @POST("posts/{postId}")
//    fun updatePost(
//        @Field("content") content: String,
//        @Field("image") image: String
//    ): Call<UpdatePostResponse>
//
////    @FormUrlEncoded
////    @POST("posts/search")
////    fun searchPost(
////        @Field("content") content: String
////    ): Call<SearchPostRespone>
//
//    @POST("posts/reactions/{postId}")
//    fun reactPost(
//        @Field("reaction") reaction: String
//    ): Call<ReactPostResponse>
}
* */