package com.example.cs426_final_project.api

import com.example.cs426_final_project.models.data.FoodDataModel
import com.example.cs426_final_project.models.data.SearchQueryDataModel
import com.example.cs426_final_project.models.response.SearchQueryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("food/search")
    fun searchFood(
        @Query("query") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
        @Query("distance") dis: Double,
    ) : Call<SearchQueryResponse>
}