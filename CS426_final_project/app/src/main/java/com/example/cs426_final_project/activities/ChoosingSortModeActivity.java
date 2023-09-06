package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.SortModeData;

public class ChoosingSortModeActivity extends AppCompatActivity {

    Button nearByButton, bestRatingsButton, popularButton;

    private void chooseNearByMode() {
        this.nearByButton.setBackgroundResource(R.drawable.chosen_sort_mode_customization);
        this.bestRatingsButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.popularButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
    }

    private void chooseBestRatingsByMode() {
        this.nearByButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.bestRatingsButton.setBackgroundResource(R.drawable.chosen_sort_mode_customization);
        this.popularButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
    }

    private void choosePopularMode() {
        this.nearByButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.bestRatingsButton.setBackgroundResource(R.drawable.unchosen_sort_mode_customization);
        this.popularButton.setBackgroundResource(R.drawable.chosen_sort_mode_customization);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_choosing_sort_mode);
        this.setUpButtons();
    }

    private void setUpButtons() {
        final SortModeData.Mode initialMode = (new SortModeData(this)).getMode();

        this.nearByButton = this.findViewById(R.id.near_by_option_in_sort_modes);
        this.bestRatingsButton = this.findViewById(R.id.best_rating_option_in_sort_modes);
        this.popularButton = this.findViewById(R.id.popular_option_in_sort_modes);

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
    }
}