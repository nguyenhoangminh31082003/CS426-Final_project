package com.example.cs426_final_project.models.response

import com.example.cs426_final_project.models.data.FoodDataModel

data class SearchQueryResponse(
    val status: String,
    val results: List<FoodDataModel>
)

data class SearchResultFields(
    val model: String,
    val pk: Int,
    val fields: FoodDataModel
)

