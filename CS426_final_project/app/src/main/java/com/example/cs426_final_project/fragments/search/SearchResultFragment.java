package com.example.cs426_final_project.fragments.search;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.activities.ReviewActivity;
import com.example.cs426_final_project.adapters.SearchResultFoodsAdapter;
import com.example.cs426_final_project.SortModeData;
import com.example.cs426_final_project.activities.ChoosingSortModeActivity;
import com.example.cs426_final_project.api.SearchApi;
import com.example.cs426_final_project.fragments.decoration.WaitingFragment;
import com.example.cs426_final_project.models.data.FoodDataModel;
import com.example.cs426_final_project.models.data.SearchQueryDataModel;
import com.example.cs426_final_project.models.response.FoodSearchResultResponse;
import com.example.cs426_final_project.models.response.SearchQueryResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultFragment extends Fragment {
    private SearchResultFoodsAdapter adapter;
    private SearchQueryDataModel searchQueryDataModel;
    private WaitingFragment waitingFragment;
    private SearchQueryResponse foodDataModels;

    RecyclerView viewOfListOfFoods;

    private boolean getNewResult = false;

    public void setGetNewResult(boolean getNewResult) {
        this.getNewResult = getNewResult;
    }
    // create result register launcher
//    private ActivityResultLauncher<Intent> reviewsResultLauncher;

    private void showWaitingFragment() {
        if(waitingFragment == null)
            waitingFragment = new WaitingFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fcvWaitingSearchResult, waitingFragment)
                .commit();
    }

    private void hideWaitingFragment() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvWaitingSearchResult, new Fragment())
                .commit();
    }


    private void setUpTestingListOfFoods(@NonNull View view) {
        RecyclerView viewOfListOfFoods = view.findViewById(R.id.list_of_foods_in_search_result);

        this.adapter = new SearchResultFoodsAdapter();
        viewOfListOfFoods.setAdapter(this.adapter);

        viewOfListOfFoods.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        reviewsResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if(result.getResultCode() == ReviewActivity.RESULT_OK) {
//                System.out.println("OK");
//            }
//        });




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(com.example.cs426_final_project.R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TextView tvNoResult = view.findViewById(R.id.tvNoResult);
        //tvNoResult.setText(String.format("No result for %s", searchQuery));
        this.setUpTestingListOfFoods(view);
        this.enableChoosingSortMode(view);

        this.waitingFragment = new WaitingFragment();
        viewOfListOfFoods = view.findViewById(R.id.list_of_foods_in_search_result);
    }

    private void showSearchResult() {
        this.callApiSearchResult(this.searchQueryDataModel);

    }

    @Override
    public void onResume() {
        super.onResume();
        TextView sortModeView = requireView().findViewById(R.id.sort_mode_in_search_result);
        sortModeView.setText((new SortModeData(requireActivity())).getReadableMode());

        if(getNewResult) {
            showWaitingFragment();
            showSearchResult();
            getNewResult = false;
        }

    }


    private void enableChoosingSortMode(@NonNull View view) {
        TextView sortModeView = view.findViewById(R.id.sort_mode_in_search_result);

        sortModeView.setText((new SortModeData(requireActivity())).getModeAsString());
        sortModeView.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), ChoosingSortModeActivity.class);
            startActivity(intent);
        });
    }

    private void callApiSearchResult(SearchQueryDataModel searchQueryDataModel) {
        SearchApi searchApi = ApiUtilityClass.Companion.getApiClient(requireContext()).create(SearchApi.class);
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
                    foodDataModels = response.body();

                    if (foodDataModels != null) {
                        // debug food data model
                        System.out.println("food data model in api call: " + foodDataModels.getResults().get(0).getFood().getName());
                        System.out.println("food data model in api call: " + foodDataModels.getResults().get(0).getFood().getPrice());

                        System.out.println("food data model in api call: " + foodDataModels.getResults().get(0).getFood().getStore());

                        showSuggestions(foodDataModels.getResults());
                    }
                    hideWaitingFragment();
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

    private void showSuggestions(List<FoodSearchResultResponse> FoodSearchResultResponseList) {
        adapter = new SearchResultFoodsAdapter(FoodSearchResultResponseList, (position, view) -> {
                Intent intent = new Intent(requireActivity(), ReviewActivity.class);

                FoodSearchResultResponse item = FoodSearchResultResponseList.get(position);
                FoodDataModel foodDataModel = item.getFood();
                // debug food data model
                System.out.println("food data model: " + foodDataModel.getName());
                System.out.println("food data model: " + foodDataModel.getId());
                System.out.println("food data model: " + foodDataModel.getStore());
                System.out.println("food data model: " + foodDataModel.getImageLink());


                intent.putExtra("foodId", foodDataModel.getId());
                intent.putExtra("storeId", foodDataModel.getStore());
                intent.putExtra("imageLink", foodDataModel.getImageLink());
                intent.putExtra("foodName", foodDataModel.getName());

                startActivity(intent);

        });
        viewOfListOfFoods.setAdapter(adapter);
        viewOfListOfFoods.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    public void setSearchQueryDataModel(SearchQueryDataModel searchQueryDataModel) {
        this.searchQueryDataModel = searchQueryDataModel;
    }
}