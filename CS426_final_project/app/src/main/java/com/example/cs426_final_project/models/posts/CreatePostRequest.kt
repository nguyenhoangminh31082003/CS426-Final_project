package com.example.cs426_final_project.models.posts

data class CreatePostRequest(
    var title: String,
    var body: String,
    var rating: Int,
    var image_name: String,
    var image_base64: String,
    var food_id: Int
)