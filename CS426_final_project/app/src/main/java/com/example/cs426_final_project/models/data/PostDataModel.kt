package com.example.cs426_final_project.models.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PostDataModel(
    var id: Int = -1,
    var user: String = "",
    var title: String = "",
    var body: String = "",
    var rating: Int = 1 ,
    @SerializedName("image_link")
    var imageLink: String = "",
    @SerializedName("created_at")
    var createAt: Date = Date()
)