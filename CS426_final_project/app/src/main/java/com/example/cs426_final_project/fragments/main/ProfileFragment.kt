package com.example.cs426_final_project.fragments.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat.getSystemService
import com.example.cs426_final_project.R
import com.example.cs426_final_project.notifications.CustomDialog
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.utilities.widgets.WidgetUtilityClass
import kotlin.math.abs


class ProfileFragment : MainPageFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    private var composeView: ComposeView? = null
    private var showDialog = mutableStateOf(false)
    private var currentEmail = mutableStateOf("")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView = view.findViewById(R.id.comvProfile)

        initComposeView(composeView)

        initWidgetSetUp(view)

        initChangeEmail(view)

        initClose(view)

        val ibAvatar = view.findViewById<ImageButton>(R.id.ibAvatar)

        loadImage(ibAvatar)

        pickImage(ibAvatar)
        val cvProfile = view.findViewById<CardView>(R.id.cvProfile)
        val etUsername = view.findViewById<EditText>(R.id.etUsername)

        // set unfocused when user click cvProfile
        cvProfile.setOnClickListener {
            etUsername.clearFocus()
            hideKeyboard(it)
        }
    }

    private fun initClose(view: View) {
        val ibClose = view.findViewById<ImageButton>(R.id.ibClose)
        ibClose.setOnClickListener {
            try {
                if (mainPageContract == null) {
                    throw Exception("mainPageContract is null")
                }
                mainPageContract!!.setToMainPage()
            } catch (e: Exception) {
                print("mainPageContract is null")
            }
        }
    }

    private fun initChangeEmail(view: View) {
        val btnChangeEmail = view.findViewById<Button>(R.id.btnChangeEmail)

        btnChangeEmail.setOnClickListener {
            showDialog.value = true
        }
    }

    private fun initWidgetSetUp(view: View) {
        val btnAddWidget = view.findViewById<Button>(R.id.btnAddWidget)

        btnAddWidget.setOnClickListener {
            WidgetUtilityClass().createWidget(requireContext())
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(requireContext(), InputMethodManager::class.java)
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initComposeView(composeView: ComposeView?) {
        composeView?.apply {
            setContent {
                if(showDialog.value) {
                    CS426_final_projectTheme {
                        CustomDialog(
                            value = currentEmail.value,
                            setShowDialog = {
                                showDialog.value = it
                            },
                            setValue = {
                                storeEmail(it)
                                currentEmail.value = it
                            },
                        )
                    }
                }

            }
        }
    }

    private fun storeEmail(email: String) {

    }

    private fun pickImage(ibAvatar: ImageButton) {
        val startPickImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val avatar = data?.getStringExtra("avatar")
                    ibAvatar.setImageResource(avatar!!.toInt())
                    storeImage(avatar)
                }
            }

        ibAvatar.setOnClickListener {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startPickImageResult.launch(intent)
                } else {
                }
            }
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun loadImage(ibAvatar: ImageButton?) {

    }


    private fun storeImage(avatar: String) {

    }

    companion object {
        @JvmStatic
        fun updateFragmentTransform(view: View, position: Float, direction: Float, relDisplacement: Float) {
            // get motion layout of
            val profileMotionLayout =
                view.findViewById<MotionLayout>(R.id.rootViewProfile) ?: return

            val progress = 1 - abs(relDisplacement)
//            print("Profile: position: $position, direction: $direction, relDisplacement: $relDisplacement, progress: $progress\n")
//            print("direction from profile: $direction\n")
            profileMotionLayout.progress = progress
        }
    }
}