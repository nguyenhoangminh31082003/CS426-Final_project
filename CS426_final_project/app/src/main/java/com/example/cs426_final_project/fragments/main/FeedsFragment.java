package com.example.cs426_final_project.fragments.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.activities.SearchActivity;
import com.example.cs426_final_project.adapters.RecyclerFeedViewPagerAdapter;
import com.example.cs426_final_project.api.FeedApi;
import com.example.cs426_final_project.api.UsersApi;
import com.example.cs426_final_project.models.FeedInfo;
import com.example.cs426_final_project.models.data.PostDataModel;
import com.example.cs426_final_project.models.data.UserDataModel;
import com.example.cs426_final_project.models.posts.FeedFields;
import com.example.cs426_final_project.models.posts.FeedResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedsFragment extends Fragment {

    private ViewPager2 vpFeed;
    private ArrayList<FeedInfo> listOfFeeds;
    private RecyclerFeedViewPagerAdapter adapter;

    public FeedsFragment() {
        this.listOfFeeds = null;
    }

    public interface OnFeedsFragmentListener {
        void onRequestToScanFood();
    }

    @Override
    public void onResume() {
        super.onResume();

        this.getFeedRequest();
    }

    private String getDateWithDefaultFormat(final String dateFromServer) {
        return dateFromServer.substring(8, 10) + "/" + dateFromServer.substring(5, 7) + "/" + dateFromServer.substring(0, 4);
    }

    private void getFeedRequest() {
        FeedApi feedApi = ApiUtilityClass.Companion.getApiClient(requireContext()).create(FeedApi.class);
        Call<String> call = feedApi.getTimelineFeeds();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(
                    @NonNull Call<String> call,
                    @NonNull Response<String> response
            ) {
                if (response.isSuccessful()) {
                    String body = response.body();
                    System.out.println("Body of response: " + body);
                    if (body != null) {
                        Gson gson = new Gson();
                        FeedResponse[] feedResponses = gson.fromJson(body, FeedResponse[].class);;
                        System.out.println("Number of feeds: " + feedResponses.length);

                        listOfFeeds.clear();
                        for (FeedResponse feedResponse : feedResponses) {
                            FeedFields feedFields = feedResponse.fields;
                            listOfFeeds.add(new FeedInfo(
                                    feedResponse.id,
                                    feedFields.username,
                                    feedFields.imageLink,
                                    feedFields.body,
                                    getDateWithDefaultFormat(feedFields.createAt)
                            ));

                            //System.out.println("User name: " + listOfFeeds.get(i).getFeedUsername());
                            //System.out.println("Image link: " + listOfFeeds.get(i).getFeedImageUri());
                        }

                        adapter = new RecyclerFeedViewPagerAdapter(listOfFeeds, position -> {
                            Intent intent = new Intent(
                                    requireActivity(),
                                    SearchActivity.class
                            );
                            System.out.println("Clicked on " + position);
                            intent.putExtra(
                                    "food_id",
                                    adapter.getFoodID(position)
                            );
                            startActivity(intent);
                        });

                        vpFeed.setAdapter(adapter);
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<String> call,
                    Throwable t
            ) {
                t.printStackTrace();
                System.err.println("Can not get feeds");
            }
        });
    }

    private void resetFeedsToDefault() {
        Uri uri = Uri.parse("android.resource://com.example.cs426_final_project/drawable/food_comment_page_demo_food_image");
        this.listOfFeeds = new ArrayList<>();
        this.listOfFeeds.add(
                new FeedInfo(
                        1,
                        "Mr. Bean",
                        uri.toString(),
                        "This is a very good food",
                        "10/10/2021"
                )
        );
        this.vpFeed.setAdapter(null);

        this.adapter = new RecyclerFeedViewPagerAdapter(this.listOfFeeds, position -> {
            System.out.println("Clicked on " + position + "!!!");
            /*
            System.out.println("Clicked on " + position + "!!!");
            Intent intent = null;
            try {
                intent = new Intent(
                        requireActivity(),
                        SearchActivity.class
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Create intent");
            intent.putExtra(
                    "food_id",
                    this.adapter.getFoodID(position)
            );
            System.out.println("Give data to intent");
            startActivity(intent);
            System.out.println("Start activity " + String.valueOf(this.adapter.getFoodID(position)));
             */
        });

        this.vpFeed.setAdapter(this.adapter);
    }

    public OnFeedsFragmentListener listener;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_feed,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton ibToScan = view.findViewById(R.id.ibToScan);

        this.vpFeed = view.findViewById(R.id.vpFeed);

        ibToScan.setOnClickListener(v -> {
            if (listener != null)
                listener.onRequestToScanFood();
        });

        ImageButton ibFeedCamera = view.findViewById(R.id.ibFeedCamera);
        ibFeedCamera.setOnClickListener(v -> {
            if (listener != null)
                listener.onRequestToScanFood();
        });

        ImageButton ibFeedFoodSearch = view.findViewById(R.id.ibFeedFoodSearch);
        ibFeedFoodSearch.setOnClickListener(v -> {
            Intent intent = new Intent(
                    getActivity(),
                    SearchActivity.class
            );
            startActivity(intent);
        });

        this.resetFeedsToDefault();

        this.getFeedRequest();
    }
}