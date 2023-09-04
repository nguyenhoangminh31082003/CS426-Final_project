package com.example.cs426_final_project.contracts;

import androidx.fragment.app.Fragment;

public interface ViewPagerContract {
    Fragment createFragment(int position);
    int getItemCount();
}
