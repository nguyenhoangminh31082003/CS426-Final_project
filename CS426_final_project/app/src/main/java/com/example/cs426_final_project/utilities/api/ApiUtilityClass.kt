package com.example.cs426_final_project.utilities.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.cs426_final_project.models.response.StatusResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiUtilityClass {

    companion object {

        private const val BASE_URL = "https://9f09-137-132-26-237.ngrok-free.app"
        fun parseError(errorBody: ResponseBody?) : StatusResponse {
            val gson = Gson()
            val type = object : TypeToken<StatusResponse>() {}.type
            var errorResponse: StatusResponse? = null
            try {
                errorResponse = gson.fromJson(errorBody!!.charStream(), type)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return errorResponse!!
        }

        fun getApiClient(context:Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient(context))
                .build()
        }
        fun <T> debug(response: retrofit2.Response<T>) {
            println("Oh no, oh no, Error errorBody: ${response.errorBody()}")
            println("Oh no, oh no, Error message: ${response.message()}")
            println("Oh no, oh no, Error code: ${response.code()}")
            println("Oh no, oh no, Error raw: ${response.raw()}")
            println("Oh no, oh no, Error body: ${response.body()}")
            try {
                val error = parseError(response.errorBody())
                println("Oh no, oh no, Error error: $error")
            } catch (e: Exception) {
                println("charStream: ${response.errorBody()?.charStream()}")
                println("Cannot parse Error: ${e.message}")
            }

        }

        @SuppressLint("ApplySharedPref")
// Modify the createOkHttpClient function to include stored cookies in the headers
        private fun createOkHttpClient(context: Context): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()

            // Create a shared preference to store cookies
            val cookiePrefs: SharedPreferences =
                context.getSharedPreferences("Cookies_Prefs", Context.MODE_PRIVATE)

            // Retrieve stored cookies
            val storedCookies = cookiePrefs.getStringSet("Set-Cookie", HashSet())

            // Interceptor to store and retrieve cookies
            val cookieInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                // Add stored cookies to request headers
                if (storedCookies != null) {
                    for (cookie in storedCookies) {
                        requestBuilder.addHeader("Cookie", cookie)
                    }
                }

                val request = requestBuilder.build()
                val response = chain.proceed(request)

                // Store any new cookies received in the response
                if (response.headers("Set-Cookie").isNotEmpty()) {
                    val cookies = HashSet<String>()
                    for (header in response.headers("Set-Cookie")) {
                        cookies.add(header)
                    }
                    val prefsEditor = cookiePrefs.edit()
                    prefsEditor.putStringSet("Set-Cookie", cookies)
                    prefsEditor.commit()
                }

                response
            }

            // Add the cookie interceptor to OkHttpClient
            clientBuilder.addInterceptor(cookieInterceptor)

            return clientBuilder.build()
        }

    }


}