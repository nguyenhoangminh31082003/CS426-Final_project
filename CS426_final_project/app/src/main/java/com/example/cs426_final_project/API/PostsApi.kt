package com.example.cs426_final_project.API

import com.example.cs426_final_project.models.Posts.CreatePostRespone
import com.example.cs426_final_project.models.Posts.ReactPostRespone
import com.example.cs426_final_project.models.Posts.UpdatePostRespone
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostsApi {

    @Multipart
    @POST("posts")
    fun createPost(
        @Part image: MultipartBody.Part,
        @Part("rating") rating: RequestBody,
        @Part("image_name") imageName: RequestBody
    ): Call<CreatePostRespone>

    @FormUrlEncoded
    @POST("posts/{postId}")
    fun updatePost(
        @Field("content") content: String,
        @Field("image") image: String
    ): Call<UpdatePostRespone>

//    @FormUrlEncoded
//    @POST("posts/search")
//    fun searchPost(
//        @Field("content") content: String
//    ): Call<SearchPostRespone>

    @FormUrlEncoded
    @POST("posts/reactions/{postId}")
    fun reactPost(
        @Field("reaction") reaction: String
    ): Call<ReactPostRespone>
}