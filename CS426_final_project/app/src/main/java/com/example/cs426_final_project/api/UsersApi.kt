package com.example.cs426_final_project.api

import com.example.cs426_final_project.models.response.LoginResponse
import com.example.cs426_final_project.models.response.RegisterResponse
import com.example.cs426_final_project.models.data.LoginDataModel
import com.example.cs426_final_project.models.data.ProfileDataModel
import com.example.cs426_final_project.models.data.RegisterDataModel
import com.example.cs426_final_project.models.data.UserDataModel
import com.example.cs426_final_project.models.response.FindFriendResponse
import com.example.cs426_final_project.models.response.ProfileResponse
import com.example.cs426_final_project.models.response.StatusResponse
import com.example.cs426_final_project.models.response.SuggestionFields
import com.example.cs426_final_project.models.response.SuggestionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApi {
    @POST("users/login")
    fun userLogin(
        @Body loginInfo: LoginDataModel
    ): Call<StatusResponse>

    @POST("users/register")
    fun userRegister(
        @Body registerDataModel: RegisterDataModel
    ): Call<RegisterResponse>

    @GET("users/profile")
    fun getLoggedProfile(): Call<ProfileResponse>

    @PUT("users/profile")
    fun updateProfile(
        @Body profileDataModel: ProfileDataModel
    ): Call<StatusResponse>

    @POST("users/logout")
    fun userLogout(): Call<StatusResponse>

    @GET("users/profile/{userID}")
    fun getUser(
        @Path(value="userID") id : Int
    ): Call<UserDataModel>

    @GET("users/friends/{userID}")
    fun getFriendsOfUser(@Path(value="userID") id : Int): Call<FindFriendResponse>

    @GET("users/friends")
    fun getFriendsOfCurrentUser(): Call<FindFriendResponse>

    @GET("users/friends/suggestions")
    fun getSomeSuggestions(): Call<List<SuggestionResponse> >

    @POST("users/friends/{friendID}")
    fun changeFriend(@Path(value="friendID") id : Int): Call<String>
}