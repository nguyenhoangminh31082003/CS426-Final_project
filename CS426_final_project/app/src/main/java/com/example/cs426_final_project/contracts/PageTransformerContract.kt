package com.example.cs426_final_project.contracts

import android.view.View

interface PageTransformerContract {
    fun onPageTransform(view : View, position : Float)
}