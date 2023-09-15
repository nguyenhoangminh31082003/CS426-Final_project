package com.example.cs426_final_project.models.response

import com.example.cs426_final_project.models.data.StoreDataModel

data class StoreResponse(
    val status : String,
    val result : StoreDataModel
)

