package com.example.cs426_final_project.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cs426_final_project.contracts.ViewPagerContract;

public class ViewPagerAdapter extends FragmentStateAdapter {

    ViewPagerContract contract;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ViewPagerContract contract) {
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
        return this.contract.getItemCount();
    }
}
