package com.example.cs426_final_project.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.contracts.MainPageContract

open class MainPageFragment(
) : Fragment() {
    var mainPageContract: MainPageContract? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}