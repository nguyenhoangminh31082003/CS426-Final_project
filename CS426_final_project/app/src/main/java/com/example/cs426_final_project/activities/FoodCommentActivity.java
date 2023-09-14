package com.example.cs426_final_project.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cs426_final_project.Helper;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.api.PostsApi;
import com.example.cs426_final_project.fragments.SurveyDetailFragment;
import com.example.cs426_final_project.models.posts.CreatePostRequest;
import com.example.cs426_final_project.models.posts.StatusResponse;
import com.example.cs426_final_project.models.response.CreatePostResponse;
import com.example.cs426_final_project.utilities.ImageUtilityClass;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodCommentActivity extends AppCompatActivity {

    private EditText etFoodComment;
    private Button btnFoodCommentDone;
    FragmentContainerView fcvSurvey;
    SurveyDetailFragment surveyDetailFragment;
    ImageView ivPreviewImage;
    ImageButton ibToScan;
    LinearLayout llFoodComment;
    MaterialButton mbAddInfo;

    private void enableComment(final boolean enable) {
        this.etFoodComment.setEnabled(enable);
        this.btnFoodCommentDone.setEnabled(enable);
        this.llFoodComment.setVisibility(enable ? LinearLayout.VISIBLE : LinearLayout.GONE);
    }

    private String getImageBase64() {
        Bitmap bitmap = ((BitmapDrawable)ivPreviewImage.getDrawable()).getBitmap();;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void sendPost() {
        PostsApi postsApi = ApiUtilityClass.Companion.getApiClient(this).create(PostsApi.class);
        postsApi.createPost(new CreatePostRequest(
                Helper.getRandomStringOfAlphabets(12),
                Helper.getRandomStringOfAlphabets(12),
                Helper.getRandomIntegerInRange(1, 5),
                Helper.getRandomStringOfAlphabets(12),
                getImageBase64()
        )).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful()) {
                    System.out.println("response: " + response.body());
                } else {
                    System.out.println("response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                System.out.println("response: " + t.getMessage());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_food_comment);

        this.mbAddInfo = findViewById(R.id.mbAddInfo);
        this.mbAddInfo.setOnClickListener(v -> {
            surveyLocation();
        });

        this.ibToScan = findViewById(R.id.ibToScan);
        this.ibToScan.setOnClickListener(v -> finish());

        this.llFoodComment = findViewById(R.id.llFoodComment);

        this.ivPreviewImage = findViewById(R.id.ivPreviewImage);

        this.etFoodComment = findViewById(R.id.etFoodComment);
        this.btnFoodCommentDone = findViewById(R.id.btnFoodCommentDone);

        this.btnFoodCommentDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        });

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
        this.enableComment(false);
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

    private void doSurvey(
            String question,
            SurveyDetailFragment.SurveyDetailFragmentListener listener
    ) {


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
        ImageUtilityClass.Companion.cropCenter(ivPreviewImage);
    }

}