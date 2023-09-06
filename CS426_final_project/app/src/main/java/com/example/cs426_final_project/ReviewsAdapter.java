package com.example.cs426_final_project;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageOfFoodOfAuthor;
        public TextView authorUserName, colorPoint, greyPoint, fullReview;

        public ViewHolder(View itemView) {
            super(itemView);

            this.imageOfFoodOfAuthor = itemView.findViewById(R.id.food_image_of_author);
            this.authorUserName = itemView.findViewById(R.id.name_of_author_of_review);
            this.colorPoint = itemView.findViewById(R.id.color_point);
            this.greyPoint = itemView.findViewById(R.id.grey_point);
        }

    }

}
