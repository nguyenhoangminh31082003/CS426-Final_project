package com.example.cs426_final_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.adapters.ViewPagerAdapter;
import com.example.cs426_final_project.fragments.access.RegisterComposeFragmentKt;
import com.example.cs426_final_project.fragments.access.RegisterFragment;
import com.example.cs426_final_project.fragments.access.WelcomeFragment;
import com.example.cs426_final_project.contracts.LoginContract;
import com.example.cs426_final_project.contracts.ViewPagerContract;
import com.example.cs426_final_project.fragments.access.LoginComposeFragmentKt;

public class SignInActivity extends AppCompatActivity {

    // create enum for fragment
    public static class enumPage {
        public static final int WELCOME = 1;
        public static final int REGISTER = 0;
        public static final int LOGIN = 2;
    }
    ViewPagerAdapter adapter;
    ViewPager2 vpSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sign_in_page);
        this.setUpViewPager();
    }

    private void setUpViewPager() {
        vpSignIn = this.findViewById(R.id.vpSignIn);
        setUpAdapter();
        vpSignIn.setCurrentItem(1, false);
        vpSignIn.setOffscreenPageLimit(1);
        vpSignIn.setUserInputEnabled(false);
    }

    private void setUpAdapter() {
        adapter = new ViewPagerAdapter(this, new ViewPagerContract() {
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = null;
                if (position == enumPage.WELCOME)
                    fragment = createWelcomeFragment();
                if (position == enumPage.LOGIN) {
                    fragment = LoginComposeFragmentKt.newInstance(getSignInContract());
                }
                if(position == enumPage.REGISTER){
                    fragment = createRegisterFragment();
                }
                return fragment;
            }
            @Override
            public int getItemCount() {
                return 3;
            }
        });
        this.vpSignIn.setAdapter(this.adapter);
    }

    @NonNull
    private Fragment createRegisterFragment() {
        Fragment fragment;
        fragment = RegisterComposeFragmentKt.newInstance(new RegisterFragment.RegisterContract() {
            @Override
            public void onSuccessRegister() {
                vpSignIn.setCurrentItem(enumPage.LOGIN, true);
            }

            @Override
            public void onUnSuccessRegister() {
                vpSignIn.setCurrentItem(enumPage.WELCOME, true);
            }
        });
        return fragment;
    }

    @NonNull
    private WelcomeFragment createWelcomeFragment() {
        return WelcomeFragment.newInstance(new WelcomeFragment.WelcomeContract() {
            @Override
            public void signIn() {
                vpSignIn.setCurrentItem(enumPage.LOGIN, true);
            }

            @Override
            public void register() {
                vpSignIn.setCurrentItem(enumPage.REGISTER, true);
            }
        });
    }

    @NonNull
    private LoginContract getSignInContract() {
        return new LoginContract() {
            @Override
            public void signIn() {
                vpSignIn.setCurrentItem(enumPage.LOGIN, true);
            }

            @Override
            public void returnToWelcome() {
                vpSignIn.setCurrentItem(enumPage.WELCOME, true);
            }

            @Override
            public void confirmEmail() {
                // set result ok for intent
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }
}