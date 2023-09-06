package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.ReviewsAdapter;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView listOfReviewsView;
    private ReviewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_review);
        this.setUpListOfReviewsView();
    }

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