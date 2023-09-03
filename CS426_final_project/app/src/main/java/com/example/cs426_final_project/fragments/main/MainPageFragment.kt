package com.example.cs426_final_project.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.contracts.MainPageContract

open class MainPageFragment(
) : Fragment() {
    protected lateinit var mainPageContract: MainPageContract
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            mainPageContract = activity as MainPageContract
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement MainPageContract")
        }
    }

}