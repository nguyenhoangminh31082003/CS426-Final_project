package com.example.cs426_final_project.utilities

import com.example.cs426_final_project.models.User.ErrorResponse
import com.example.cs426_final_project.models.User.LoginResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException

class ApiUtilityClass {
    companion object {
        fun parseError(response: Response<LoginResponse>) : ErrorResponse {
            val gson = Gson()
            val type = object : TypeToken<ErrorResponse>() {}.type
            var errorResponse: ErrorResponse? = null
            try {
                errorResponse = gson.fromJson(response.errorBody()!!.charStream(), type)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return errorResponse!!

        }

        const val BASE_URL = "https://12e2-137-132-26-94.ngrok-free.app"
    }


}