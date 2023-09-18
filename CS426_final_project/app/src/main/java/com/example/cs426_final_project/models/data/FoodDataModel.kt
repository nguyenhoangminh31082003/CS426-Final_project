package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class FoodDataModel(
    val id: Int,
    val name: String,
    @SerializedName("store")
    val store: Int,
    @SerializedName("image_link")
    val imageLink: String,
    val price: Double,
)
