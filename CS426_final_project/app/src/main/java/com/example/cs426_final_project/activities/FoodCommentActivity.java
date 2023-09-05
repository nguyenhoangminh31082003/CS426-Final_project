package com.example.cs426_final_project.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        ImageView ivPreviewImage = findViewById(R.id.ivPreviewImage);
        ivPreviewImage.setImageURI(previewImageUri);



    }

}