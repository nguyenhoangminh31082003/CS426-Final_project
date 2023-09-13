package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class ProfileDataModel(
    @SerializedName("full_name")
    val name: String,
    @Transient
    val email: String,
    val avatar: String,
    val bio : String,
)