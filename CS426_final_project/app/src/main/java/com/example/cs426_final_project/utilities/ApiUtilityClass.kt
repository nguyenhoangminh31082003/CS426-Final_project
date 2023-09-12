package com.example.cs426_final_project.utilities

import com.example.cs426_final_project.models.response.ErrorResponse
import com.example.cs426_final_project.models.response.LoginResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

class ApiUtilityClass {
    companion object {
        fun parseError(errorBody: ResponseBody?) : ErrorResponse {
            val gson = Gson()
            val type = object : TypeToken<ErrorResponse>() {}.type
            var errorResponse: ErrorResponse? = null
            try {
                errorResponse = gson.fromJson(errorBody!!.charStream(), type)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return errorResponse!!
        }

        const val BASE_URL = " https://1a4b-137-132-26-94.ngrok-free.app"
    }


}