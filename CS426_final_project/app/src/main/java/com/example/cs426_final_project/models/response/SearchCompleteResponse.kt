package com.example.cs426_final_project.models.response

data class SearchCompleteResponse(
    val status: String,
    val results: List<String>
)