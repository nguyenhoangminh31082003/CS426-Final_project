package com.example.cs426_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
//import android.graphics.Camera;
import android.hardware.Camera;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class FoodScanPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_food_scan_page);

        if (this.checkCameraHardware(this))
            System.out.println("OK, camera hardware!!!!");
        else
            System.err.println("NOT OK!!!, CAMERA HARDWARE!!!");

        this.checkCameraPermissions(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_control_part, CameraFragment.newInstance())
                .commit();
    }



    private static void checkCameraPermissions(Context context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            System.err.println("NOT GRANTED!!!!!");
            ActivityCompat.requestPermissions((Activity) context,
                    new String[] { Manifest.permission.CAMERA },
                    100);
        } else
            System.err.println("GRANTED!!!!");
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