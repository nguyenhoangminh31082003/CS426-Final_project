package com.example.cs426_final_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.ReviewsAdapter;
import com.example.cs426_final_project.Singleton.UserLocation;
import com.example.cs426_final_project.api.StoreApi;
import com.example.cs426_final_project.models.data.StoreDataModel;
import com.example.cs426_final_project.models.response.StoreResponse;
import com.example.cs426_final_project.utilities.ImageUtilityClass;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView listOfReviewsView;
    private ReviewsAdapter adapter;
    private TextView tvFoodReviewStoreName;
    private TextView tvFoodReviewStoreAddress;
    private TextView tvFoodReviewStoreDistance;
    private TextView tvFoodReviewFoodName;
    private ImageView ivFoodReviewPreview;
    private Integer foodId;
    private String foodName;
    private Integer storeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_review);
        tvFoodReviewStoreName = findViewById(R.id.tvFoodReviewStoreName);
        tvFoodReviewStoreAddress = findViewById(R.id.tvFoodReviewStoreAddress);
        tvFoodReviewStoreDistance = findViewById(R.id.tvFoodReviewStoreDistance);
        tvFoodReviewFoodName = findViewById(R.id.txtFoodReviewFoodName);

        // get data from previous activity
        Intent intent = getIntent();
        foodId = intent.getIntExtra("foodId", 0);
        storeId = intent.getIntExtra("storeId", 0);
        foodName = intent.getStringExtra("foodName");

        tvFoodReviewFoodName.setText(foodName);

        loadPreviewImage(intent);
        callGetStoreReviewsApi();


        this.setUpListOfReviewsView();
    }

    private void loadPreviewImage(Intent intent) {
        String imageLink = intent.getStringExtra("imageLink");
        ivFoodReviewPreview = findViewById(R.id.ivFoodReviewPreview);
        if (imageLink != null) {
            ImageUtilityClass.Companion.loadSquareImageViewFromUrl(
                    ivFoodReviewPreview,
                    imageLink, 600);
        }
    }

    private void callGetStoreReviewsApi() {
        StoreApi storeApi = ApiUtilityClass.Companion.getApiClient(this).create(StoreApi.class);
        storeApi.getStoreById(storeId).enqueue(new retrofit2.Callback<com.example.cs426_final_project.models.response.StoreResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<StoreResponse> call, @NonNull retrofit2.Response<StoreResponse> response) {
                if(response.isSuccessful()) {
                    StoreResponse storeResponse = response.body();

                    StoreDataModel storeDataModel = null;
                    if (storeResponse != null) {
                        storeDataModel = storeResponse.getResult();
                    }
                    if(storeDataModel != null) {
                        tvFoodReviewStoreName.setText(storeDataModel.getName());
                        tvFoodReviewStoreAddress.setText(storeDataModel.getAddress());
                        String formattedDistance = getFormattedUserStoreDistance(storeDataModel.getLat(), storeDataModel.getLong());
                        tvFoodReviewStoreDistance.setText(formattedDistance);
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<com.example.cs426_final_project.models.response.StoreResponse> call, @NonNull Throwable t) {
                System.out.println("Failed to get store by id with message: " + t.getMessage());
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private String getFormattedUserStoreDistance(double lat, double aLong) {
        double deltaLat = lat - UserLocation.INSTANCE.getLatitude();
        double deltaLong = aLong - UserLocation.INSTANCE.getLongitude();
        double distance =  Math.sqrt(deltaLat * deltaLat + deltaLong * deltaLong);
        return String.format("%.2f", distance) + " km (From your location)";
    }

    @SuppressLint("SetTextI18n")
    private void setUpListOfReviewsView() {
        TextView reviewCountView = this.findViewById(R.id.review_count_in_reviews_page);

        this.listOfReviewsView = this.findViewById(R.id.review_food_reviews_list);

        this.adapter = new ReviewsAdapter();

        this.listOfReviewsView.setAdapter(this.adapter);

        final int reviewCount = this.adapter.getItemCount();
        if (reviewCount <= 0)
            reviewCountView.setText("No review yet");
        else
            reviewCountView.setText(String.valueOf(reviewCount));

        this.listOfReviewsView.setLayoutManager(new LinearLayoutManager(this));
    }
}