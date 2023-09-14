package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class ReviewDataModel(
    val id: Int,
    val user : Int,
    val username : String,
    val title : String,
    val body : String,
    val food : Int,
    val rating : Int,
    @SerializedName("image_link")
    val imageLink : String,
    @SerializedName("create_at")
    val createAt : String,
)
