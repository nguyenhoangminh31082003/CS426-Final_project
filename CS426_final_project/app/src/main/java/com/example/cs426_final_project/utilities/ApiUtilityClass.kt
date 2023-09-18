package com.example.cs426_final_project.utilities

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

        // change default debug to false to use production server
        private fun getBaseUrl(debug : Boolean = true): String {
            if(debug){
                return "https://6855-137-132-26-190.ngrok-free.app"
            }
            return "http://13.229.250.243"
        }

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

        @JvmOverloads
        fun getApiClient(context:Context, clearCookie : Boolean = false): Retrofit {
            return Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient(context, clearCookie))
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
        private fun createOkHttpClient(context: Context, clearCookie: Boolean): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()

            val cookiePrefs: SharedPreferences =
                context.getSharedPreferences("Cookies_Prefs", Context.MODE_PRIVATE)

            val storedCookies = cookiePrefs.getStringSet("Set-Cookie", HashSet())

            val cookieInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                if (storedCookies != null) {
                    for (cookie in storedCookies) {
                        requestBuilder.addHeader("Cookie", cookie)
                    }
                }

                val request = requestBuilder.build()
                val response = chain.proceed(request)

                println("debug cookie: ${storedCookies.toString()}")

                println("debug cookie: response headers: ${response.headers("Set-Cookie")}")

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