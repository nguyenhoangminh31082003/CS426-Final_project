package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class StoreDataModel (
    val id : Int,
    val name : String,
    val address : String,
    @SerializedName("latitude")
    val lat : Double,
    @SerializedName("longitude")
    val long : Double,
    @SerializedName("avg_rating")
    val avgRating : Double,
    @SerializedName("image_link")
    val imageLink : String,
)