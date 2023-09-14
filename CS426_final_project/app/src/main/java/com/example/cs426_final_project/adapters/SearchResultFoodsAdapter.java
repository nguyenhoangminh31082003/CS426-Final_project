package com.example.cs426_final_project.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs426_final_project.ConceptualListOfFoodsInSearchResult;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.models.response.SearchResultFields;
import com.example.cs426_final_project.utilities.ImageUtilityClass;

import java.util.List;

public class SearchResultFoodsAdapter extends RecyclerView.Adapter<SearchResultFoodsAdapter.ViewHolder> {

    private ConceptualListOfFoodsInSearchResult listOfFoods;

    private List<SearchResultFields> searchResultFieldsList;

    public SearchResultFoodsAdapter() {
        this.listOfFoods = new ConceptualListOfFoodsInSearchResult();
    }

    public SearchResultFoodsAdapter(List<SearchResultFields> results) {
        this.searchResultFieldsList = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if(this.searchResultFieldsList != null) {
            SearchResultFields searchResultFields = this.searchResultFieldsList.get(position);
            String url = searchResultFields.getFields().getImageLink();
            // load image from url
            ImageUtilityClass.Companion.loadImageFromUrl(holder.ivSearchResultFoodPreview, url);
            holder.txtSearchResultFoodName.setText(searchResultFields.getFields().getName());
            holder.txtSearchResultReview.setText("Try to be the first one to review");
            ImageUtilityClass.Companion.cropCenter(holder.ivSearchResultFoodPreview);
            return;
        }

        holder.txtSearchResultFoodName.setText(this.listOfFoods.getFoodName(position));
        holder.txtSearchResultReview.setText(this.listOfFoods.getARandomReview(position));
        ImageUtilityClass.Companion.cropCenter(holder.ivSearchResultFoodPreview);
    }

    @Override
    public int getItemCount() {
        if(this.searchResultFieldsList != null)
            return this.searchResultFieldsList.size();

        return this.listOfFoods.getSize();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivSearchResultFoodPreview;
        public TextView txtSearchResultFoodName, txtSearchResultReview;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivSearchResultFoodPreview = itemView.findViewById(R.id.ivSearchResultFoodPreview);
            this.txtSearchResultFoodName = itemView.findViewById(R.id.txtSearchResultFoodName);
            this.txtSearchResultReview = itemView.findViewById(R.id.txtSearchResultReview);
        }

    }

}
