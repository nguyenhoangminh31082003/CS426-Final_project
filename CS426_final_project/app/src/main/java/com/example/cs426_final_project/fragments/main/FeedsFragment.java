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
import com.example.cs426_final_project.models.FeedInfo;
import com.example.cs426_final_project.models.data.PostDataModel;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedsFragment extends Fragment {

    public interface OnFeedsFragmentListener {
        void onRequestToScanFood();
    }

    private void getFeedRequest() {
        FeedApi feedApi = ApiUtilityClass.Companion.getApiClient(requireContext()).create(FeedApi.class);
        Call<PostDataModel>  call = feedApi.getTimelineFeeds();
        call.enqueue(new Callback<PostDataModel>() {
            @Override
            public void onResponse(
                    @NonNull Call<PostDataModel> call,
                    @NonNull Response<PostDataModel> response
            ) {
                if (response.isSuccessful()) {
                    System.out.println("Feeds is successfully taken?");
                } else {
                    System.err.println("Something is not right!!!");
                }
            }

            @Override
            public void onFailure(Call<PostDataModel> call, Throwable t) {
                System.err.println("Can not get feeds");
            }
        });
    }

    public OnFeedsFragmentListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton ibToScan = view.findViewById(R.id.ibToScan);
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
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        getFeedRequest();

        Uri uri = Uri.parse("android.resource://com.example.cs426_final_project/drawable/food_comment_page_demo_food_image");


        ArrayList<FeedInfo> feedList = new ArrayList<>();
        feedList.add( new FeedInfo(1,"Ms. Bean",uri.toString(),"This is a very good food","10/10/2021"));
        feedList.add( new FeedInfo(2,"Mr. Bean",uri.toString(),"This is a very good food","15/10/2021"));


        RecyclerFeedViewPagerAdapter adapter = new RecyclerFeedViewPagerAdapter(feedList, position -> {
            System.out.println("Clicked on " + position);
        });

        ViewPager2 vpFeed = view.findViewById(R.id.vpFeed);
        vpFeed.setAdapter(adapter);


    }
}