package com.example.cs426_final_project.fragments.main;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cs426_final_project.camera.CameraFragment;
import com.example.cs426_final_project.R;

public class FoodScanFragment extends MainPageFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_food_scan_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (this.checkCameraHardware(this.requireContext()))
            System.out.println("OK, camera hardware!!!!");
        else
            System.err.println("NOT OK!!!, CAMERA HARDWARE!!!");

        this.requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_control_part, CameraFragment.newInstance())
                .commit();

        this.enableViewFriends(view);

        this.enableViewProfile(view);

        this.enableStartViewFeedsIcon(view);
    }


    private void enableViewProfile(View view) {
        ImageView button = view.findViewById(R.id.food_scan_page_profile_icon_image);
        button.setOnClickListener(v -> {
            mainPageContract.foodScanToProfilePage();
        });
    }

    private void enableStartViewFeedsIcon(View view) {
        ImageView button = view.findViewById(R.id.start_view_feeds);

//        button.setOnClickListener(v -> {
//            mainPageContract.setToViewFeeds();
//        });
    }

    private void enableViewFriends(View view) {
        ImageView button = view.findViewById(R.id.food_scan_page_friend_icon_image);

        button.setOnClickListener(v -> {
            mainPageContract.foodScanToMyFriendsPage();
        });
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
}
