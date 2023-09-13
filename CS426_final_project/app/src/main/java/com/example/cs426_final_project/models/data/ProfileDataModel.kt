package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class ProfileDataModel(
    @SerializedName("full_name")
    var name: String,
    @Transient
    var email: String,
    val avatar: String,
    val bio : String,
)