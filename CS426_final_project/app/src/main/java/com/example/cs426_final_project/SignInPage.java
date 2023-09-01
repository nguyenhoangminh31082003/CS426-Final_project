package com.example.cs426_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInPage extends AppCompatActivity {

    SignInViewPageAdapter adapter;
    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sign_in_page);
        this.setViewPager();
    }

    private void setViewPager() {
        this.viewPager = this.findViewById(R.id.view_pager_in_sign_in_page);
        this.adapter = new SignInViewPageAdapter(this);
        this.viewPager.setAdapter(this.adapter);
    }
}