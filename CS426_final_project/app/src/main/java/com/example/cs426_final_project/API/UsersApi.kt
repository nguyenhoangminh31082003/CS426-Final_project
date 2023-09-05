package com.example.cs426_final_project.API

import com.example.cs426_final_project.models.User.LoginRespone
import com.example.cs426_final_project.models.User.RegisterRespone
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UsersApi {
    @FormUrlEncoded
    @POST("users/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginRespone>

    @FormUrlEncoded
    @POST("users/register")
    fun userRegister(
        @Field("full_name") fullName: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("phone_number") phoneNumber: String,
        @Field("password") password: String,
        @Field("password2") password2: String
    ): Call<RegisterRespone>
}