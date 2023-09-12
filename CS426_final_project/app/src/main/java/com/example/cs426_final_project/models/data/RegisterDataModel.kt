package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName


data class RegisterDataModel(
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