package com.example.cs426_final_project;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class SignInViewPageAdapter extends FragmentStateAdapter {

    final String[] titles = {"Welcome", "EmailReceiver"};

    public SignInViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(final int position) {
        if (position == 0)
            return new WelcomeFragment();
        if (position == 1)
            return new EmailReceiverFragment();
        return null;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
