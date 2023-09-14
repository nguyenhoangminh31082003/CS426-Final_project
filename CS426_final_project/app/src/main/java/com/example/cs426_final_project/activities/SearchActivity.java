package com.example.cs426_final_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.api.SearchApi;
import com.example.cs426_final_project.fragments.search.SearchResultFragment;
import com.example.cs426_final_project.fragments.search.SearchSuggestionFragment;
import com.example.cs426_final_project.fragments.search.TrendingFoodFragment;
import com.example.cs426_final_project.models.data.SearchQueryDataModel;
import com.example.cs426_final_project.models.response.SearchQueryResponse;
import com.example.cs426_final_project.models.response.SearchResultFields;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private androidx.appcompat.widget.SearchView sevSearch;
    private SearchQueryDataModel searchQueryDataModel;
    TrendingFoodFragment trendingFoodFragment;
    SearchSuggestionFragment searchSuggestionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_search);
        this.setSearchView();

        searchQueryDataModel = new SearchQueryDataModel();

        initBackButton();
        initSearchView();
        showTrendingFood();
    }

    private void showTrendingFood() {
        trendingFoodFragment = new TrendingFoodFragment();

        trendingFoodFragment.setOnFragmentInteractionListener(option -> {
            try {
                if(sevSearch == null)
                    throw new Exception("sevSearch is null");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            sevSearch.setQuery(option, true);
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvSearch, trendingFoodFragment)
                .commit();
    }

    private void showSuggestions(List<SearchResultFields> results){
        List<String> suggestions = new ArrayList<>();
        for (SearchResultFields result : results) {
            suggestions.add(result.getFields().getName());
        }
        searchSuggestionFragment = SearchSuggestionFragment.Companion.newInstance(suggestions);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvSearch, searchSuggestionFragment)
                .commit();
    }


    private void initSearchView() {
        sevSearch = this.findViewById(R.id.sevSearch);
        sevSearch.setQueryHint("Type here to search");
        SearchResultFragment searchResultFragment = new SearchResultFragment();

        sevSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // show search result fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fcvSearch, searchResultFragment)
                        .addToBackStack(null)
                        .commit();

                searchResultFragment.setSearchQueryDataModel(searchQueryDataModel);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty())
                    showTrendingFood();
                else {
                    // show suggestion fragment
                    searchQueryDataModel.setQuery(newText);
                }
                return false;
            }
        });
    }



    private void initBackButton() {
        AppCompatImageView btnSearchBack = this.findViewById(R.id.acivSearchBack);
        btnSearchBack.setOnClickListener(view -> finish());
    }

    private void setSearchView() {
        androidx.appcompat.widget.SearchView searchView = this.findViewById(R.id.sevSearch);
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        ImageView searchMagIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        //ImageView openButton = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        //ImageView openButton = searchView.findViewById(androidx.appcompat.R.id.search_go_btn);
        ImageView openButton = searchView.findViewById(androidx.appcompat.R.id.search_button);
        openButton.setImageResource(R.drawable.search_page_search_icon);
        searchMagIcon.setImageResource(R.drawable.search_page_search_icon);
        closeButton.setImageResource(R.drawable.search_page_close_icon);
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.white));
        searchView.setQueryHint("Type here to search");
    }

}