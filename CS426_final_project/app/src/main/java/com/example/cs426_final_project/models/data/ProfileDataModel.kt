package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class ProfileDataModel(
    @SerializedName("full_name")
    var name: String,
    var avatar: String,
    var bio : String,
    @SerializedName("avatar_base64")
    var avatarBase64 : String,
    @SerializedName("avatar_filename")
    var avatarFilename : String,
)