package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cs426_final_project.R;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView listOfReviewsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_review);
        this.setUpListOfReviewsView();
    }

    private void setUpListOfReviewsView() {
        this.listOfReviewsView = this.findViewById(R.id.review_food_reviews_list);

        this.listOfReviewsView.setLayoutManager(new LinearLayoutManager(this));
    }
}