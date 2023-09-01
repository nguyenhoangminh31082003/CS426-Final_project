package com.example.cs426_final_project.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.cs426_final_project.R
import com.example.cs426_final_project.notifications.CustomDialog
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.utilities.WidgetUtilityClass


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    // add register activity result variable


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var composeView = view.findViewById<ComposeView>(R.id.composeView)

        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                var openDialog by remember { mutableStateOf(true) }

                CS426_final_projectTheme {
                    if(openDialog){
                        CustomDialog(
                            setShowDialog = {
                                openDialog = it
                            },
                            setValue = {
                                storeEmail(it)
                            },
                        )
                    }
                }
            }
        }

        var btnChangeEmail = view.findViewById<Button>(R.id.btnChangeEmail)

        btnChangeEmail.setOnClickListener {

        }

        var ibClose = view.findViewById<ImageButton>(R.id.ibClose)

        ibClose.setOnClickListener {
            WidgetUtilityClass().createWidget(requireContext())
        }

        var ibAvatar = view.findViewById<ImageButton>(R.id.ibAvatar)

        loadImage(ibAvatar)

        pickImage(ibAvatar)
    }

    private fun storeEmail(email: String) {
        TODO("Not yet implemented")
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
                    TODO("Permission denied")
                }
            }
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun loadImage(ibAvatar: ImageButton?) {
        TODO("Not yet implemented")
    }


    private fun storeImage(avatar: String) {
        TODO("Not yet implemented")
    }


}