package com.example.cs426_final_project.models.data

import java.time.LocalDate
import java.util.Date

data class PostDataModel(
    var id: Int = -1,
    var user: String = "",
    var title: String = "",
    var body: String = "",
    var rating: Int = 1 ,
    var image_link: String = "",
    var create_at: Date = Date()
)