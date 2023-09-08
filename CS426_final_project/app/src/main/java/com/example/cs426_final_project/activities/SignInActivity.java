package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.adapters.ViewPagerAdapter;
import com.example.cs426_final_project.fragments.access.WelcomeFragment;
import com.example.cs426_final_project.contracts.SignInContract;
import com.example.cs426_final_project.contracts.ViewPagerContract;
import com.example.cs426_final_project.fragments.access.EmailReceiverComposeFragmentKt;

public class SignInActivity extends AppCompatActivity implements SignInContract {

    ViewPagerAdapter adapter;
    ViewPager2 vpSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sign_in_page);
        this.setViewPager();
    }

    private void setViewPager() {
        this.vpSignIn = this.findViewById(R.id.vpSignIn);
        this.adapter = new ViewPagerAdapter(this, new ViewPagerContract() {
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = null;
                if (position == 0)
                    fragment = new WelcomeFragment();
                if (position == 1){
                    fragment = EmailReceiverComposeFragmentKt.newInstance();
                }
                return fragment;
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });
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
    public void confirmEmail() {
        finish();
    }

}