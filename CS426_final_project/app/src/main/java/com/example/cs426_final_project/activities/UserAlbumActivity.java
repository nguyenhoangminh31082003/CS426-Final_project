package com.example.cs426_final_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cs426_final_project.AlbumAdapter;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.adapters.RecyclerFeedViewPagerAdapter;
import com.example.cs426_final_project.api.FeedApi;
import com.example.cs426_final_project.api.PostsApi;
import com.example.cs426_final_project.models.FeedInfo;
import com.example.cs426_final_project.models.posts.FeedFields;
import com.example.cs426_final_project.models.posts.FeedResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAlbumActivity extends AppCompatActivity {

    AlbumAdapter adapter;
    RecyclerView listOfImagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_user_album);

        this.enableBackButton();

        this.setUpListOfImagesView();
    }

    private void setUpListOfImagesView() {
        this.listOfImagesView = this.findViewById(R.id.list_of_images_of_album);
        this.adapter = new AlbumAdapter();

        PostsApi postsApi = ApiUtilityClass
                .Companion
                .getApiClient(this)
                .create(PostsApi.class);
        Call<List<FeedResponse>> call = postsApi.getFeedsPostedByUser();
        call.enqueue(new Callback<List<FeedResponse>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<FeedResponse>> call,
                    @NonNull Response<List<FeedResponse>> response
            ) {
                if (response.isSuccessful()) {
                    List<FeedResponse> feedResponseList = response.body();
                    if (feedResponseList != null) {
                        List<String> imageLinks = new ArrayList<String>();

                        System.out.println("Number of photos: " + feedResponseList.size());

                        for (FeedResponse feedResponse : feedResponseList) {
                            FeedFields feedFields = feedResponse.fields;

                            imageLinks.add(feedFields.imageLink);
                        }

                        adapter = new AlbumAdapter(imageLinks);

                        listOfImagesView.setAdapter(adapter);
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<List<FeedResponse>> call,
                    Throwable t
            ) {
                t.printStackTrace();
                System.err.println("Can not get feeds");
            }
        });

        this.listOfImagesView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void enableBackButton() {
        ImageView button = this.findViewById(R.id.album_back_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}