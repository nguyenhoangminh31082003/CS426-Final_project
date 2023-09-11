package com.example.cs426_final_project.API

import com.example.cs426_final_project.models.User.LoginResponse
import com.example.cs426_final_project.models.User.RegisterResponse
import com.example.cs426_final_project.models.viewmodel.RegisterUiModel
//import com.example.cs426_final_project.models.viewmodel.RegisterUiModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UsersApi {
    @FormUrlEncoded
    @POST("users/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("users/register")
    fun userRegister(
        @Body registerUiModel: RegisterUiModel
    ): Call<RegisterResponse>
}