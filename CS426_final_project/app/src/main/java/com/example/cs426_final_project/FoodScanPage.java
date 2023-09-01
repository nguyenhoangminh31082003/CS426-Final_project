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
import android.widget.FrameLayout;

public class FoodScanPage extends AppCompatActivity {

    private Camera camera;
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_food_scan_page);

        if (this.checkCameraHardware(this))
            System.out.println("OK, camera hardware!!!!");
        else
            System.err.println("NOT OK!!!, CAMERA HARDWARE!!!");

        this.checkCameraPermissions(this);

        this.camera = getCameraInstance();

        this.cameraPreview = new CameraPreview(this, camera);

        this.setPreview();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        ViewGroup.LayoutParams layoutParams = preview.getLayoutParams();

        super.onWindowFocusChanged(hasFocus);

        System.err.println("WIDTH: " + preview.getLayoutParams().width);
        System.err.println("HEIGHT: " + preview.getLayoutParams().height);

        layoutParams.height = layoutParams.width;

        preview.setBackground(this.getDrawable(R.drawable.camera_preview_customization));

        System.err.println("WIDTH: " + preview.getLayoutParams().width);
        System.err.println("HEIGHT: " + preview.getLayoutParams().height);
    }

    private void setPreview() {
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        ViewGroup.LayoutParams layoutParams = preview.getLayoutParams();

        preview.setLayoutParams(layoutParams);

        preview.addView(this.cameraPreview);
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

    private static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            System.err.println("WHERE ARE YOU????? CAMERA!!!!!!!!!!!!!!!");
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}