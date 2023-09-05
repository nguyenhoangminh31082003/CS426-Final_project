package com.example.cs426_final_project.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cs426_final_project.R;


public class FoodCommentActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_food_comment);

        Intent intent = getIntent();

        Uri previewImageUri = Uri.parse(intent.getStringExtra("imageUri"));
//        Bitmap previewImageBitmap = BitmapFactory.decodeFile(previewImageUri.getPath());
        // print width and height of bitmap
//        System.out.println("width: " + previewImageBitmap.getWidth() + " height: " + previewImageBitmap.getHeight());

        ImageView ivPreviewImage = findViewById(R.id.ivPreviewImage);
        ivPreviewImage.setImageURI(previewImageUri);

        // preserve ratio 1:1 of ivPreviewImage
        ivPreviewImage.setAdjustViewBounds(true);
        ivPreviewImage.setScaleType(ImageView.ScaleType.CENTER_CROP);



//        Bitmap bitmap = ((BitmapDrawable)ivPreviewImage.getDrawable()).getBitmap();
//        System.out.println("width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());


    }

}