package com.example.cs426_final_project.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
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
import com.example.cs426_final_project.api.FeedApi;
import com.example.cs426_final_project.api.PostsApi;
import com.example.cs426_final_project.fragments.SurveyDetailFragment;
import com.example.cs426_final_project.models.data.PostDataModel;
import com.example.cs426_final_project.models.posts.CreatePostRequest;
import com.example.cs426_final_project.models.posts.StatusResponse;
import com.example.cs426_final_project.utilities.ImageUtilityClass;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodCommentActivity extends AppCompatActivity {

    private int rating;
    private EditText etFoodComment;
    private Button btnFoodCommentDone;

    private EditText foodNameText;
    FragmentContainerView fcvSurvey;
    SurveyDetailFragment surveyDetailFragment;
    ImageView ivPreviewImage;
    ImageButton ibToScan;
    LinearLayout llFoodComment;
    MaterialButton mbAddInfo;

    public FoodCommentActivity() {
        super();
        this.rating = 3;
    }

    private void enableComment(final boolean enable) {
        this.foodNameText.setEnabled(enable);
        this.etFoodComment.setEnabled(enable);
        this.btnFoodCommentDone.setEnabled(enable);
        this.llFoodComment.setVisibility(enable ? LinearLayout.VISIBLE : LinearLayout.GONE);
    }

    private void enableFiveRatingIcons() {
        AppCompatImageView[] icons = {
                this.findViewById(R.id.rating_level_1_icon),
                this.findViewById(R.id.rating_level_2_icon),
                this.findViewById(R.id.rating_level_3_icon),
                this.findViewById(R.id.rating_level_4_icon),
                this.findViewById(R.id.rating_level_5_icon)
        };
        final int[] chosenImages = {
                R.drawable.chosen_score_face_icon_level_1,
                R.drawable.chosen_score_face_icon_level_2,
                R.drawable.chosen_score_face_icon_level_3,
                R.drawable.chosen_score_face_icon_level_4,
                R.drawable.chosen_score_face_icon_level_5,
        };
        final int[] unchosenImages = {
                R.drawable.unchosen_score_face_icon_level_1,
                R.drawable.unchosen_score_face_icon_level_2,
                R.drawable.unchosen_score_face_icon_level_3,
                R.drawable.unchosen_score_face_icon_level_4,
                R.drawable.unchosen_score_face_icon_level_5,
        };

        icons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = 1;
                icons[0].setImageResource(chosenImages[0]);
                icons[1].setImageResource(unchosenImages[1]);
                icons[2].setImageResource(unchosenImages[2]);
                icons[3].setImageResource(unchosenImages[3]);
                icons[4].setImageResource(unchosenImages[4]);
            }
        });

        icons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = 2;
                icons[0].setImageResource(unchosenImages[0]);
                icons[1].setImageResource(chosenImages[1]);
                icons[2].setImageResource(unchosenImages[2]);
                icons[3].setImageResource(unchosenImages[3]);
                icons[4].setImageResource(unchosenImages[4]);
            }
        });

        icons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = 3;
                icons[0].setImageResource(unchosenImages[0]);
                icons[1].setImageResource(unchosenImages[1]);
                icons[2].setImageResource(chosenImages[2]);
                icons[3].setImageResource(unchosenImages[3]);
                icons[4].setImageResource(unchosenImages[4]);
            }
        });

        icons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = 4;
                icons[0].setImageResource(unchosenImages[0]);
                icons[1].setImageResource(unchosenImages[1]);
                icons[2].setImageResource(unchosenImages[2]);
                icons[3].setImageResource(chosenImages[3]);
                icons[4].setImageResource(unchosenImages[4]);
            }
        });

        icons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = 5;
                icons[0].setImageResource(unchosenImages[0]);
                icons[1].setImageResource(unchosenImages[1]);
                icons[2].setImageResource(unchosenImages[2]);
                icons[3].setImageResource(unchosenImages[3]);
                icons[4].setImageResource(chosenImages[4]);
            }
        });
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
                this.foodNameText.getText().toString(),
                this.etFoodComment.getText().toString(),
                this.rating,
                Helper.getRandomStringOfAlphabets(12),
                this.getImageBase64(),
                Helper.getRandomIntegerInRange(1, 310823)
        )).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(
                    Call<StatusResponse> call,
                    Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("Send successfully");
                } else {
                    System.err.println("Something is not ok? Please check quick");
                    ApiUtilityClass.Companion.debug(response);
                }
            }

            @Override
            public void onFailure(
                    Call<StatusResponse> call,
                    Throwable t
            ) {
                System.err.println("OMG");
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_food_comment);

        this.rating = 3;
        this.enableFiveRatingIcons();

        this.mbAddInfo = this.findViewById(R.id.mbAddInfo);
        this.mbAddInfo.setOnClickListener(v -> {
            surveyLocation();
        });

        this.ibToScan = this.findViewById(R.id.ibToScan);
        this.ibToScan.setOnClickListener(v -> finish());

        this.foodNameText = this.findViewById(R.id.food_comment_food_name);

        this.llFoodComment = this.findViewById(R.id.llFoodComment);

        this.ivPreviewImage = this.findViewById(R.id.ivPreviewImage);

        this.etFoodComment = this.findViewById(R.id.etFoodComment);
        this.btnFoodCommentDone = this.findViewById(R.id.btnFoodCommentDone);

        this.btnFoodCommentDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
                finish();
            }
        });

        this.showPreviewImage(getIntent());

        this.fcvSurvey = this.findViewById(R.id.fcvSurvey);
        this.surveyLocation();
        testBitmap();
    }

    private  void testBitmap() {
        Bitmap bitmap = ((BitmapDrawable)ivPreviewImage.getDrawable()).getBitmap();
        System.out.println("width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());
    }

    private void surveyLocation() {
        this.enableComment(false);
        this.doSurvey("The tag of the location", new SurveyDetailFragment.SurveyDetailFragmentListener() {
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
            final String question,
            SurveyDetailFragment.SurveyDetailFragmentListener listener
    ) {

        surveyDetailFragment = new SurveyDetailFragment();

        surveyDetailFragment.setQuestionTitle(question);
        surveyDetailFragment.setSurveyDetailFragmentListener(listener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvSurvey, surveyDetailFragment)
                .commit();
    }

    private void showPreviewImage(
            Intent intent
    ) {
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