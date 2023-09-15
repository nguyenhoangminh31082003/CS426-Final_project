package com.example.cs426_final_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs426_final_project.models.data.PostDataModel;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private ConceptualListOfReviewsAboutOneSpecificFood listOfReviews;

    List<PostDataModel> reviewsList;

    public ReviewsAdapter() {
        listOfReviews = new ConceptualListOfReviewsAboutOneSpecificFood();
    }

    public ReviewsAdapter(List<PostDataModel> reviewsList) {
        this.reviewsList = reviewsList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_reviews, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if(reviewsList != null){
            PostDataModel review = reviewsList.get(position);
            holder.authorUserName.setText(review.getUsername());
            holder.fullReview.setText(review.getBody());
            int rating = review.getRating();
            holder.colorPoint.setText(String.valueOf(rating));
            holder.colorPoint.setTextColor(chooseTextColor(rating));
            return;
        }

        final int colorPoint = this.listOfReviews.getColorPoint(position);
        holder.greyPoint.setText(this.listOfReviews.getGreyPoint(position) + "d");
        holder.colorPoint.setText(String.valueOf(colorPoint));
        holder.authorUserName.setText(this.listOfReviews.getUserName(position));
        holder.fullReview.setText(this.listOfReviews.getFullReview(position));
        int textColor = chooseTextColor(colorPoint);
        holder.colorPoint.setTextColor(textColor);
    }

    private int chooseTextColor(int colorPoint) {
        if (colorPoint == 1)
            return Color.parseColor("#ff0500");
        else if (colorPoint == 2)
            return Color.parseColor("#ffa500");
        else if (colorPoint == 3)
            return Color.parseColor("#ffff00");
        else if (colorPoint == 4)
            return Color.parseColor("#9acd32");
        else if (colorPoint == 5)
            return Color.parseColor("#90ee90");
        else
            return Color.parseColor("#000000");
    }

    @Override
    public int getItemCount() {
        if(reviewsList != null)
            return reviewsList.size();

        return this.listOfReviews.getSize();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
