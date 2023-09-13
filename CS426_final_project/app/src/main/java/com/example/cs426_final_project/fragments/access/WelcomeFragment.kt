package com.example.cs426_final_project.fragments.access

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.R
import com.google.android.material.button.MaterialButton

const val USER_PREFERENCES_NAME = "profile_preferences"


class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    interface WelcomeContract {
        fun login()
        fun register()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mbLogin = view.findViewById<MaterialButton>(R.id.mbLogin)
        val mbRegister = view.findViewById<MaterialButton>(R.id.mbRegister)
        mbLogin.setOnClickListener {
            contract?.login()

        }
        mbRegister.setOnClickListener {
            contract?.register()
        }
    }

    private fun testSharePreferences() {
        val sharePreferences = requireActivity().getSharedPreferences("test", Context.MODE_PRIVATE)
        sharePreferences.edit().putString("test", "test").apply()
    }




    private var contract: WelcomeContract? = null

    companion object {
        fun newInstance(
            welcomeContract: WelcomeContract?
        ): WelcomeFragment {
            val fragment = WelcomeFragment()
            fragment.contract = welcomeContract
            return fragment
        }
    }
}