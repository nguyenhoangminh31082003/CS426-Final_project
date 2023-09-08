package com.example.cs426_final_project.models.viewmodel

// create view model for register activity

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cs426_final_project.API.UsersApi
import com.example.cs426_final_project.models.User.RegisterResponse
import com.example.cs426_final_project.storage.ProfilePreferences
import com.example.cs426_final_project.utilities.ApiUtilityClass
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

class RegisterViewModel(
    private val profilePreferences: ProfilePreferences
) : ViewModel(){
    var registerUiModel by mutableStateOf(RegisterUiModel())

    private val profilePreferencesFlow = profilePreferences.profilePreferencesFlow
    private fun callApiRegister() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiUtilityClass.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: UsersApi = retrofit.create(UsersApi::class.java)
        val call = apiService.userRegister(registerUiModel)

        call.enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onResponse(
                call: retrofit2.Call<RegisterResponse>,
                response: retrofit2.Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        saveProfileInfo()

                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
               // throw message from server return
                throw Exception("Error: ${t.message}")
            }
        })
    }

    private fun saveProfileInfo() {
        viewModelScope.launch {
            profilePreferences.updateProfileInfo(
                userId = registerUiModel.username,
                username = registerUiModel.fullName,
                avatarUri = "",
                email = registerUiModel.email
            )
        }
    }

    // update register info
    fun confirmRegisterInfo(){
        try {
            callApiRegister()
        } catch (e: Exception) {
            throw e
        }
    }

}

class RegisterViewModelFactory(
    private val profilePreferences: ProfilePreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(
                profilePreferences = profilePreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}