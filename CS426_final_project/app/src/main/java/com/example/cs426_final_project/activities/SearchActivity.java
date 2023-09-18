package com.example.cs426_final_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.Singleton.UserLocation;
import com.example.cs426_final_project.api.FoodApi;
import com.example.cs426_final_project.api.SearchApi;
import com.example.cs426_final_project.fragments.search.SearchResultFragment;
import com.example.cs426_final_project.fragments.search.SearchSuggestionFragment;
import com.example.cs426_final_project.fragments.search.TrendingFoodFragment;
import com.example.cs426_final_project.models.data.FoodDataModel;
import com.example.cs426_final_project.models.data.SearchQueryDataModel;
import com.example.cs426_final_project.models.response.FoodResponse;
import com.example.cs426_final_project.models.response.SearchCompleteResponse;
import com.example.cs426_final_project.utilities.ApiUtilityClass;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    enum SearchType {
        TRENDING,
        SUGGESTION,
        RESULT
    }

    private SearchType currentSearchType;
    private androidx.appcompat.widget.SearchView sevSearch;
    private SearchQueryDataModel searchQueryDataModel;
    private boolean isLoadingSearchComplete = false;
    private boolean isChangedWithoutNotify = false;
    TrendingFoodFragment trendingFoodFragment;
    SearchSuggestionFragment searchSuggestionFragment;
    SearchResultFragment searchResultFragment;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_search);

        this.setSearchView();

        this.searchQueryDataModel = new SearchQueryDataModel();
        searchQueryDataModel.setLimit(10);

        this.initBackButton();
        this.initSearchView();
        this.showTrendingFood();

        checkInitialQuery();
    }

    private void checkInitialQuery() {
        try {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if (extras != null)
                this.queryFoodNameWithGivenID(extras.getInt("food_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void queryFoodNameWithGivenID(final int id) {
        FoodApi foodApi =
                ApiUtilityClass.Companion.getApiClient(this).create(FoodApi.class);;
        Call<FoodResponse> call = foodApi.getFood(id);

        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<FoodResponse> call,
                    @NonNull Response<FoodResponse> response
            ) {
                if (response.isSuccessful()) {
                    FoodResponse body = response.body();
                    assert body != null;
                    FoodDataModel data = body.results;

                    System.out.println("Successfully response with food");
                    changeQuerySearch(data.getName(), true, false);

                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<FoodResponse> call,
                    Throwable t
            ) {
                t.printStackTrace();
                System.err.println("Can not get food");
            }
        });
    }

    private void showTrendingFood() {
        if(currentSearchType == SearchType.TRENDING)
            return;
        currentSearchType = SearchType.TRENDING;

        trendingFoodFragment = new TrendingFoodFragment();

        trendingFoodFragment.setOnFragmentInteractionListener(option -> {
            try {
                if(sevSearch == null)
                    throw new Exception("sevSearch is null");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            changeQuerySearch(option, true, false);
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvSearch, trendingFoodFragment)
                .commit();
    }

    private void showSuggestions(List<String> results){
        currentSearchType = SearchType.SUGGESTION;

        searchSuggestionFragment = SearchSuggestionFragment.Companion.newInstance(results,
                (view, position, item) -> changeQuerySearch(item, true, false)
        );

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvSearch, searchSuggestionFragment)
                .commit();
    }

    private void callSearchCompleteApi(){
        SearchApi searchApi = ApiUtilityClass.Companion.getApiClient(this).create(SearchApi.class);

        Call<SearchCompleteResponse> call = searchApi.searchAutoComplete(
                searchQueryDataModel.getQuery().toLowerCase(),
                searchQueryDataModel.getLimit()
        );
        call.enqueue(new Callback<SearchCompleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchCompleteResponse> call, @NonNull Response<SearchCompleteResponse> response) {
                if(response.isSuccessful()) {
                    SearchCompleteResponse searchCompleteResponse = response.body();
                    List<String> foodNames = Objects.requireNonNull(searchCompleteResponse).getResults();
                    System.out.println("Successfully response with food names");
                    System.out.println(foodNames);
                    System.out.println(searchQueryDataModel.getQuery());
                    if(searchQueryDataModel.getQuery().length() > 3)
                        showSuggestions(foodNames);
                } else {
                    System.err.println("Something is not ok? Please check quick");
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchCompleteResponse> call, @NonNull Throwable t) {
                System.out.println("OMG");
                System.out.println(t.getMessage());
            }
        });
    }
    void changeQuerySearch(String query, boolean submit, boolean notify) {
        isChangedWithoutNotify = !notify;
        searchQueryDataModel.setQuery(query);
        sevSearch.setQuery(query, submit);
    }

    private void initSearchView() {
        sevSearch = this.findViewById(R.id.sevSearch);
        sevSearch.setQueryHint("Type here to search");

        sevSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearchType = SearchType.RESULT;
                searchResultFragment = new SearchResultFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fcvSearch, searchResultFragment)
                        .addToBackStack(null)
                        .commit();

                searchQueryDataModel.setLat(UserLocation.INSTANCE.getLatitude());
                searchQueryDataModel.setLong(UserLocation.INSTANCE.getLongitude());

                searchResultFragment.setSearchQueryDataModel(searchQueryDataModel);
                searchResultFragment.setGetNewResult(true);

                return false;
            }



            @Override
            public boolean onQueryTextChange(String newText) {

                if(isChangedWithoutNotify) {
                    isChangedWithoutNotify = false;
                    return false;
                }

                System.out.println("Query: " + newText);
                searchQueryDataModel.setQuery(newText);
                if(newText.length() <= 3){
                    if(newText.isEmpty()){
                        System.out.println("Empty query");
                    }
                    showTrendingFood();
                }
                else {
                    callSearchCompleteApi();
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
        ImageView openButton = searchView.findViewById(androidx.appcompat.R.id.search_button);
        openButton.setImageResource(R.drawable.search_page_search_icon);
        searchMagIcon.setImageResource(R.drawable.search_page_search_icon);
        closeButton.setImageResource(R.drawable.search_page_close_icon);
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.white));
        searchView.setQueryHint("Type here to search");
    }

}