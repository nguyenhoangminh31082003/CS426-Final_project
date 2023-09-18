package com.example.cs426_final_project.api

import com.example.cs426_final_project.models.response.StoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApi {
    // create api for calling /stores/<int:store_id>/
    @GET("stores/{store_id}")
    fun getStoreById(@Path("store_id") storeId: Int): Call<StoreResponse>
}