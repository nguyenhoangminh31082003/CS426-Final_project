package com.example.cs426_final_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs426_final_project.ConceptualListOfFoodsInSearchResult;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.utilities.ImageUtilityClass;

public class SearchResultFoodsAdapter extends RecyclerView.Adapter<SearchResultFoodsAdapter.ViewHolder> {

    private ConceptualListOfFoodsInSearchResult listOfFoods;

    public SearchResultFoodsAdapter() {
        this.listOfFoods = new ConceptualListOfFoodsInSearchResult();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_of_food_in_search_result, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.foodName.setText(this.listOfFoods.getFoodName(position));
        holder.aRandomReview.setText(this.listOfFoods.getARandomReview(position));
        ImageUtilityClass.Companion.cropCenter(holder.foodDemoImage);
    }

    @Override
    public int getItemCount() {
        return this.listOfFoods.getSize();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView foodDemoImage;
        public TextView foodName, aRandomReview;

        public ViewHolder(View itemView) {
            super(itemView);
            this.foodDemoImage = itemView.findViewById(R.id.demo_image_of_food);
            this.foodName = itemView.findViewById(R.id.food_name);
            this.aRandomReview = itemView.findViewById(R.id.a_random_food_review);
        }

    }

}
