package com.example.cs426_final_project.api;

import com.example.cs426_final_project.models.response.FoodResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodApi {

    @GET("food/{foodID}")
    Call<FoodResponse> getFood(@Path(value="foodID") final int id);

}
