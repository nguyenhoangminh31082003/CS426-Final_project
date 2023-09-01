package com.example.cs426_final_project.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cs426_final_project.contracts.SignInViewPagerContract;

import java.util.ArrayList;

public class SignInViewPageAdapter extends FragmentStateAdapter {

    final String[] titles = {"Welcome", "EmailReceiver"};

    SignInViewPagerContract contract;

    public SignInViewPageAdapter(@NonNull FragmentActivity fragmentActivity, SignInViewPagerContract contract) {
        super(fragmentActivity);
        this.contract = contract;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.contract.createFragment(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
