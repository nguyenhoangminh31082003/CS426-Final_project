package com.example.cs426_final_project.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.fragments.SurveyDetailFragment;
import com.google.android.material.button.MaterialButton;


public class FoodCommentActivity extends AppCompatActivity {

    private EditText etFoodComment;
    private Button btnFoodCommentDone;
    FragmentContainerView fcvSurvey;
    SurveyDetailFragment surveyDetailFragment;
    ImageView ivPreviewImage;
    ImageButton ibToScan;
    LinearLayout llFoodComment;
    MaterialButton mbAddInfo;

    private void enableComment(boolean enable) {
        etFoodComment.setEnabled(enable);
        btnFoodCommentDone.setEnabled(enable);
        llFoodComment.setVisibility(enable ? LinearLayout.VISIBLE : LinearLayout.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_food_comment);

        mbAddInfo = findViewById(R.id.mbAddInfo);
        mbAddInfo.setOnClickListener(v -> {
            surveyLocation();
        });

        ibToScan = findViewById(R.id.ibToScan);
        ibToScan.setOnClickListener(v -> finish());

        llFoodComment = findViewById(R.id.llFoodComment);

        ivPreviewImage = findViewById(R.id.ivPreviewImage);

        etFoodComment = findViewById(R.id.etFoodComment);
        btnFoodCommentDone = findViewById(R.id.btnFoodCommentDone);

        Intent intent = getIntent();

        showPreviewImage(intent);

        fcvSurvey = findViewById(R.id.fcvSurvey);
        surveyLocation();
        testBitmap();
    }

    private  void testBitmap() {
        Bitmap bitmap = ((BitmapDrawable)ivPreviewImage.getDrawable()).getBitmap();
        System.out.println("width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());
    }

    private void surveyLocation() {
        enableComment(false);
        doSurvey("The tag of the location", new SurveyDetailFragment.SurveyDetailFragmentListener() {
            @Override
            public void onClose() {
                getSupportFragmentManager().beginTransaction()
                        .remove(surveyDetailFragment)
                        .commit();
                enableComment(true);
            }

            @Override
            public void onDone(@NonNull String answer) {
                System.out.println("answer: " + answer);
                enableComment(true);
            }
        });
    }

    private void doSurvey(String question, SurveyDetailFragment.SurveyDetailFragmentListener listener) {


        surveyDetailFragment = new SurveyDetailFragment();

        surveyDetailFragment.setQuestionTitle(question);
        surveyDetailFragment.setSurveyDetailFragmentListener(listener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvSurvey, surveyDetailFragment)
                .commit();
    }

    private void showPreviewImage(Intent intent) {
        Uri previewImageUri = Uri.parse(intent.getStringExtra("imageUri"));
//        Bitmap previewImageBitmap = BitmapFactory.decodeFile(previewImageUri.getPath());
        // print width and height of bitmap
//        System.out.println("width: " + previewImageBitmap.getWidth() + " height: " + previewImageBitmap.getHeight());

        ImageView ivPreviewImage = findViewById(R.id.ivPreviewImage);
        ivPreviewImage.setImageURI(previewImageUri);

        // preserve ratio 1:1 of ivPreviewImage
        ivPreviewImage.setAdjustViewBounds(true);
        ivPreviewImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

}