package com.example.cs426_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        SearchResultFoodsAdapter.ViewHolder viewHolder = new SearchResultFoodsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.foodName.setText(this.listOfFoods.getFoodName(position));
        holder.aRandomReview.setText(this.listOfFoods.getARandomReview(position));
    }

    @Override
    public int getItemCount() {
        return this.listOfFoods.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
