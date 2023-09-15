package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName

data class SearchQueryDataModel(
    var query: String = "",
    var offset : Int = 0,
    var limit : Int = 10,

    @SerializedName("latitude")
    var lat : Double = 0.0,

    @SerializedName("longitude")
    var long : Double = 0.0,

    @SerializedName("distance")
    var dis : Double = 0.0,
)