package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class FoodDataModel(
    @Transient
    val id: Int,
    val name: String,
    val store: Int,
    @SerializedName("image_link")
    val imageLink: String,
    val price: Double,
)
