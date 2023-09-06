package com.example.cs426_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private ConceptualListOfReviewsAboutOneSpecificFood listOfReviews;

    public ReviewsAdapter() {
        listOfReviews = new ConceptualListOfReviewsAboutOneSpecificFood();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_for_review, parent, false);

        ReviewsAdapter.ViewHolder viewHolder = new ReviewsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.greyPoint.setText(this.listOfReviews.getGreyPoint(position) + "d");
        holder.colorPoint.setText(String.valueOf(this.listOfReviews.getColorPoint(position)));
        holder.authorUserName.setText(this.listOfReviews.getUserName(position));
        holder.fullReview.setText(this.listOfReviews.getFullReview(position));
    }

    @Override
    public int getItemCount() {
        return this.listOfReviews.getSize();
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
            this.fullReview = itemView.findViewById(R.id.full_review_of_author_about_food);
        }

    }

}
