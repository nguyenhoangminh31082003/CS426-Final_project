package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.SortModeData;

public class ChoosingSortModeActivity extends AppCompatActivity {

    Button nearByButton, bestRatingsButton, popularButton;

    private void chooseNearByMode() {
        this.nearByButton.setBackgroundResource(R.drawable.chosen_sort_mode_customization);
        this.nearByButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_grey));
        this.bestRatingsButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.bestRatingsButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        this.popularButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.popularButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }

    private void chooseBestRatingsByMode() {
        this.nearByButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.nearByButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        this.bestRatingsButton.setBackgroundResource(R.drawable.chosen_sort_mode_customization);
        this.bestRatingsButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_grey));
        this.popularButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.popularButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }

    private void choosePopularMode() {
        this.nearByButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.nearByButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        this.bestRatingsButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.bestRatingsButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        this.popularButton.setBackgroundResource(R.drawable.chosen_sort_mode_customization);
        this.popularButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_grey));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_choosing_sort_mode);
        this.setUpButtons();
        this.setUpBackButton();
    }

    private void setUpBackButton() {
        ImageView button = this.findViewById(R.id.back_button_in_choosing_sort_modes);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUpButtons() {
        System.out.println("Start setting up buttons");

        final SortModeData.Mode initialMode = (new SortModeData(this)).getMode();

        System.out.println("Find initial mode!!!");

        this.nearByButton = this.findViewById(R.id.near_by_option_in_sort_modes);
        this.bestRatingsButton = this.findViewById(R.id.best_rating_option_in_sort_modes);
        this.popularButton = this.findViewById(R.id.popular_option_in_sort_modes);

        System.out.println("Find buttons corresponding to modes");

        if (initialMode == SortModeData.Mode.NEAR_BY)
            this.chooseNearByMode();
        else if (initialMode == SortModeData.Mode.BEST_RATINGS)
            this.chooseBestRatingsByMode();
        else if (initialMode == SortModeData.Mode.POPULAR)
            this.choosePopularMode();

        System.out.println("Set up initial mode");

        this.nearByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseNearByMode();
                (new SortModeData(ChoosingSortModeActivity.this)).setMode(SortModeData.Mode.NEAR_BY, ChoosingSortModeActivity.this);
            }
        });

        this.bestRatingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseBestRatingsByMode();
                (new SortModeData(ChoosingSortModeActivity.this)).setMode(SortModeData.Mode.BEST_RATINGS, ChoosingSortModeActivity.this);
            }
        });

        this.popularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePopularMode();
                (new SortModeData(ChoosingSortModeActivity.this)).setMode(SortModeData.Mode.POPULAR, ChoosingSortModeActivity.this);
            }
        });

        System.out.println("Set up events");
    }
}