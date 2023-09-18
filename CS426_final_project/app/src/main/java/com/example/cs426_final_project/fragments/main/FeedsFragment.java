package com.example.cs426_final_project.fragments.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.cs426_final_project.Helper;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.activities.SearchActivity;
import com.example.cs426_final_project.activities.UserAlbumActivity;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedsFragment extends Fragment {

    private ViewPager2 vpFeed;
    private ArrayList<FeedInfo> listOfFeeds;
    private RecyclerFeedViewPagerAdapter adapter;
    private boolean getFeedRequestOnResume = true;

    private ActivityResultLauncher<Intent> shareImageLauncher;
    private ActivityResultLauncher<Intent> searchFoodLauncher;

    public FeedsFragment() {
        this.listOfFeeds = null;
    }

    public interface OnFeedsFragmentListener {
        void onRequestToScanFood();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.getFeedRequestOnResume) {
            this.getFeedRequest();
        }
        this.vpFeed.setCurrentItem(0);
        this.getFeedRequestOnResume = true;
    }

    private String getDateWithDefaultFormat(
            final String dateFromServer
    ) {
        return dateFromServer.substring(8, 10) + "/" + dateFromServer.substring(5, 7) + "/" + dateFromServer.substring(0, 4);
    }

    private void getFeedRequest() {
        FeedApi feedApi = ApiUtilityClass
                .Companion
                .getApiClient(requireContext())
                .create(FeedApi.class);
        Call<List<TimelineFeedResponse> > call = feedApi.getTimelineWidget();
        call.enqueue(new Callback<List<TimelineFeedResponse>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<TimelineFeedResponse> > call,
                    @NonNull Response<List<TimelineFeedResponse> > response
            ) {
                if (response.isSuccessful()) {
                    List<TimelineFeedResponse> feedResponseList = response.body();
                    if (feedResponseList != null) {

                        System.out.println("Number of feeds: " + feedResponseList.size());

                        listOfFeeds.clear();
                        for (TimelineFeedResponse feedResponse : feedResponseList)
                            listOfFeeds.add(new FeedInfo(
                                    feedResponse.id,
                                    feedResponse.fullName,
                                    feedResponse.imageLink,
                                    feedResponse.body,
                                    getDateWithDefaultFormat(feedResponse.createAt)
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
                            searchFoodLauncher.launch(intent);
                        });

                        vpFeed.setAdapter(adapter);
                    }
                } else {
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<List<TimelineFeedResponse> > call,
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

        ImageButton ibFeedPhotoShare = view.findViewById(R.id.ibFeedPhotoShare);
        ibFeedPhotoShare.setOnClickListener(view1 -> {
            final String link = adapter.getImageLink(vpFeed.getCurrentItem());

            if (link.startsWith("http"))
                (new DownloadImageTask()).execute(link);
        });

        ImageButton ibFeedViewAlbum = view.findViewById(R.id.ibFeedViewAlbum);

        ibFeedViewAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(getActivity(), UserAlbumActivity.class);
                startActivity(intent);
                */
            }
        });

        this.resetFeedsToDefault();

        this.getFeedRequest();

        shareImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    getFeedRequestOnResume = false;
                });

        searchFoodLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    getFeedRequestOnResume = false;
                });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String link = params[0];

            try {
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                String path = MediaStore.Images.Media.insertImage(
                        requireContext().getContentResolver(),
                        bitmap,
                        "DownloadedImageNumber" + Helper.getRandomIntegerInRange(3108, 31082003),
                        null
                );

                Uri uri = Uri.parse(path);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                shareImageLauncher.launch(Intent.createChooser(intent, "Share photo"));
            }
        }
    }

}