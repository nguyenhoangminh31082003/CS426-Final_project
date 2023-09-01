package com.example.cs426_final_project.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cs426_final_project.CameraFragment;
import com.example.cs426_final_project.R;

public class FoodScanActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_food_scan_page);

        if (this.checkCameraHardware(this))
            System.out.println("OK, camera hardware!!!!");
        else
            System.err.println("NOT OK!!!, CAMERA HARDWARE!!!");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_control_part, CameraFragment.newInstance())
                .commit();
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
