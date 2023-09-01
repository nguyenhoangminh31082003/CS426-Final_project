package com.example.cs426_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.cs426_final_project.contracts.SignInContract;
import com.example.cs426_final_project.contracts.SignInViewPagerContract;

public class SignInActivity extends AppCompatActivity implements SignInContract, SignInViewPagerContract {

    SignInViewPageAdapter adapter;
    ViewPager2 vpSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sign_in_page);
        this.setViewPager();
    }

    private void setViewPager() {
        this.vpSignIn = this.findViewById(R.id.vpSignIn);
        this.adapter = new SignInViewPageAdapter(this, this);
        this.vpSignIn.setAdapter(this.adapter);
    }

    @Override
    public void signIn() {
        this.vpSignIn.setCurrentItem(1);
    }

    @Override
    public void returnToWelcome() {
        this.vpSignIn.setCurrentItem(0);
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new WelcomeFragment();
        if (position == 1)
            fragment = new EmailReceiverFragment();
        return fragment;
    }
}