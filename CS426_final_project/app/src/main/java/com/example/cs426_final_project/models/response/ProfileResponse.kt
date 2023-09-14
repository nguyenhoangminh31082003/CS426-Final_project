package com.example.cs426_final_project.models.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse (
    @SerializedName("full_name")
    val fullName : String,
    val bio : String,
    val avatar : String,
)