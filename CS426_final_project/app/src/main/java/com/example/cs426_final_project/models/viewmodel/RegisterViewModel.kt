package com.example.cs426_final_project.models.viewmodel

// create view model for register activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cs426_final_project.API.UsersApi
import com.example.cs426_final_project.fragments.access.USER_PREFERENCES_NAME
import com.example.cs426_final_project.models.User.RegisterResponse
//import com.example.cs426_final_project.storage.ProfilePreferences
import com.example.cs426_final_project.utilities.ApiUtilityClass
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//
data class RegisterUiModel(
    @SerializedName("full_name")
    var fullName: String = "",

    @SerializedName("username")
    val username: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("password")
    var password : String = "",

    @SerializedName("password2")
    var confirmPassword : String = "",

    @SerializedName("phone_number")
    var phoneNumber: String = "",
)

interface RegisterViewModelContract {
    fun onRegisterSuccess()
}

class RegisterViewModel(
    private val registerViewModelContract: RegisterViewModelContract,
    private val profilePreferences: SharedPreferences
) : ViewModel(){
    var registerUiModel = mutableStateOf(RegisterUiModel())

    private fun callApiRegister() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiUtilityClass.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: UsersApi = retrofit.create(UsersApi::class.java)
        val call = apiService.userRegister(registerUiModel.value)

        // debug json in call
//        println("call: ${call.request()}")

        call.enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onResponse(
                call: retrofit2.Call<RegisterResponse>,
                response: retrofit2.Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {


                        // make sure save profile info first then call onRegisterSuccess
                        saveProfileInfo()
                        registerViewModelContract.onRegisterSuccess()

                    }
                } else {
                    // parse body by using
                    throw Exception("Oh no, oh no, Error: ${response.body()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
               // throw message from server return
                print("Oh no! Something went wrong in register view model")
                throw Exception("Error: ${t.message}")
            }
        })
    }

    @SuppressLint("ApplySharedPref")
    fun saveProfileInfo() {
        val editor = profilePreferences.edit()
        editor.putString("username", registerUiModel.value.username)
        editor.putString("password", registerUiModel.value.password)
        editor.putString("email", registerUiModel.value.email)
        editor.putString("phoneNumber", registerUiModel.value.phoneNumber)
        editor.putString("fullName", registerUiModel.value.fullName)
        editor.commit()
        println("saved username: ${profilePreferences.getString("username", "")}")
    }

    fun confirmRegisterInfo(){
        try {
            callApiRegister()
        } catch (e: Exception) {
            throw e
        }
    }

}

class RegisterViewModelFactory(
    private val registerViewModelContract: RegisterViewModelContract,
    private val profilePreferences: SharedPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(
                registerViewModelContract
                , profilePreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}