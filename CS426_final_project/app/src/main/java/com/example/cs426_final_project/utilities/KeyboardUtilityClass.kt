package com.example.cs426_final_project.utilities

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

class KeyboardUtilityClass {
    companion object{
        fun hideKeyboard(context: Context, view: View) {
            val inputMethodManager =
                ContextCompat.getSystemService(context, InputMethodManager::class.java)
            inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}