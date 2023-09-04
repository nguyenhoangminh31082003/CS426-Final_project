package com.example.cs426_final_project.fragments.main;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.cs426_final_project.camera.CameraFragment;
import com.example.cs426_final_project.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Objects;

public class FoodScanFragment extends MainPageFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_scan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (this.checkCameraHardware(this.requireContext()))
            System.out.println("OK, camera hardware!!!!");
        else
            System.err.println("NOT OK!!!, CAMERA HARDWARE!!!");

        ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        CameraFragment cameraFragment = CameraFragment.newInstance();

        cameraFragment.setCameraContract(bitmapData -> {
//            testFailStoreGallery(bitmapData);
            Uri images;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

            } else {
                images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
            Uri uri = requireActivity().getContentResolver().insert(images, contentValues);

            try {
                Bitmap bitmap = imageProcessing(bitmapData);

                OutputStream outputStream = requireActivity().getContentResolver().openOutputStream(Objects.requireNonNull(uri));

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Objects.requireNonNull(outputStream).close();

                Toast.makeText(requireContext(), "Saved to gallery", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(requireContext(), "Failed to save to gallery", Toast.LENGTH_SHORT).show();
            }

        });

        this.requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_control_part, cameraFragment)
                .commit();

        this.enableViewFriends(view);

        this.enableViewProfile(view);

        this.enableStartViewFeedsIcon(view);
    }

    private static Bitmap imageProcessing(byte[] bitmapData) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newSize = Math.min(width, height);
        int cropWidth = (width - newSize) / 2;
        int cropHeight = (height - newSize) / 2;
        bitmap = Bitmap.createBitmap(bitmap, cropWidth, cropHeight, newSize, newSize);

        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    private static void testFailStoreGallery(byte[] bitmapData) {
        try {
            FileOutputStream outputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getAbsolutePath() + "MyPics");
            dir.mkdir();

            String fileName = String.format(Locale.getDefault(), "%d.jpg", System.currentTimeMillis());

            File outFile = new File(dir, fileName);

            outputStream = new FileOutputStream(outFile);
            // convert byte array to bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            outputStream.flush();
            outputStream.close();

            } catch (Exception e) {
            e.printStackTrace();
        }
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
