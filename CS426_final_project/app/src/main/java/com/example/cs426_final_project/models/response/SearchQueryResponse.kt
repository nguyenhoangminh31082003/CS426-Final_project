package com.example.cs426_final_project.models.response

import com.example.cs426_final_project.models.data.FoodDataModel
import com.example.cs426_final_project.models.data.ReviewDataModel

data class SearchQueryResponse(
    val status: String,
    val results: List<FoodSearchResultResponse>
)

data class FoodSearchResultResponse(
    val food : FoodDataModel,
    val review : ReviewDataModel? = null,
)

data class SearchResultFields(
    val model: String,
    val pk: Int,
    val fields: FoodDataModel
)


