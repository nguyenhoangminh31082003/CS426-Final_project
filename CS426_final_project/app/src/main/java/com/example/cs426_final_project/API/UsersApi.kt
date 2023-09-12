package com.example.cs426_final_project.API

import com.example.cs426_final_project.models.User.LoginResponse
import com.example.cs426_final_project.models.User.RegisterResponse
import com.example.cs426_final_project.models.data.LoginDataModel
import com.example.cs426_final_project.models.data.RegisterDataModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApi {
    @POST("users/login")
    fun userLogin(
        @Body loginInfo: LoginDataModel
    ): Call<LoginResponse>

    @POST("users/register")
    fun userRegister(
        @Body registerDataModel: RegisterDataModel
    ): Call<RegisterResponse>
}