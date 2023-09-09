package com.example.cs426_final_project.fragments.access

import ProfileInfo
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.R
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
        fun onLogged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mbLogin = view.findViewById<MaterialButton>(R.id.mbLogin)
        val mbRegister = view.findViewById<MaterialButton>(R.id.mbRegister)
        mbLogin.setOnClickListener {
            if (contract != null) {
                if (!isProfileStored()) {
                    print("profile is not stored")
                    contract!!.login()

                } else {
                    contract!!.onLogged()
                }
            }

        }
        mbRegister.setOnClickListener {
            if (contract != null) {
                contract!!.register()
            }
        }

    }

    private fun testSharePreferences() {
        val sharePreferences = requireActivity().getSharedPreferences("test", Context.MODE_PRIVATE)
        sharePreferences.edit().putString("test", "test").apply()
    }


    private fun isProfileStored(): Boolean {
        val profilePreferences = requireContext().getSharedPreferences(
            USER_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

        // get content for profile
        val profileInfo = ProfileInfo()
//        profilePreferences.getString("userId", null).toString().also { profileInfo.userId = it }
        profilePreferences.getString("username", "").toString().also { profileInfo.username = it }
//        profilePreferences.getString("email", null).toString().also { profileInfo.email = it }
//        profilePreferences.getString("avatarUri", null).toString().also { profileInfo.avatarUri = it }

        // clear shared preferences
//        profilePreferences.edit().clear().apply()

        return profileInfo.username != ""
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