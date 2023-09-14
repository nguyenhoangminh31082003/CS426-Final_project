package com.example.cs426_final_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.api.SearchApi;
import com.example.cs426_final_project.fragments.search.SearchResultFragment;
import com.example.cs426_final_project.fragments.search.TrendingFoodFragment;
import com.example.cs426_final_project.models.data.FoodDataModel;
import com.example.cs426_final_project.models.data.SearchQueryDataModel;
import com.example.cs426_final_project.models.response.SearchQueryResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {
    private androidx.appcompat.widget.SearchView sevSearch;
    private SearchQueryDataModel searchQueryDataModel;
    TrendingFoodFragment trendingFoodFragment;
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


    private void initSearchView() {
        sevSearch = this.findViewById(R.id.sevSearch);
        sevSearch.setQueryHint("Type here to search");
        SearchResultFragment searchResultFragment = new SearchResultFragment();

        sevSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // show search result fragment
                searchResultFragment.setSearchQuery(sevSearch.getQuery().toString());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fcvSearch, searchResultFragment)
                        .addToBackStack(null)
                        .commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty())
                    showTrendingFood();
                else {
                    searchQueryDataModel.setQuery(newText);
                    callApiSuggestions(searchQueryDataModel);
                }
                return false;
            }
        });
    }

    private void callApiSuggestions(SearchQueryDataModel searchQueryDataModel) {
        SearchApi searchApi = ApiUtilityClass.Companion.getApiClient(this).create(SearchApi.class);
        Call<SearchQueryResponse> call = searchApi.searchFood(
            searchQueryDataModel.getQuery(),
            searchQueryDataModel.getLimit(),
            searchQueryDataModel.getOffset(),
            searchQueryDataModel.getLat(),
            searchQueryDataModel.getLong(),
            searchQueryDataModel.getDis()
        );
        call.enqueue(new Callback<SearchQueryResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchQueryResponse> call, @NonNull Response<SearchQueryResponse> response) {
                if(response.isSuccessful()) {
                    SearchQueryResponse foodDataModels = response.body();
//                    trendingFoodFragment.setSuggestions(foodDataModels);
                    // debug here
                    System.out.println("foodDataModels: " + foodDataModels);
                    throw new RuntimeException("foodDataModels: " + foodDataModels);
                } else {
                    System.err.println("Something is not ok? Please check quick");
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchQueryResponse> call, @NonNull Throwable t) {
                System.out.println("OMG");
                System.out.println(t.getMessage());
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