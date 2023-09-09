package com.example.cs426_final_project.fragments.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.cs426_final_project.R
import com.example.cs426_final_project.fragments.access.USER_PREFERENCES_NAME
//import com.example.cs426_final_project.models.viewmodel.ProfileViewModelFactory
import com.example.cs426_final_project.notifications.CustomDialog
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.utilities.ImageUtilityClass
import com.example.cs426_final_project.utilities.ImageUtilityClass.Companion.cropCircleBitmap
import com.example.cs426_final_project.utilities.ImageUtilityClass.Companion.cropSquareBitmap
import com.example.cs426_final_project.utilities.KeyboardUtilityClass
import com.example.cs426_final_project.utilities.WidgetUtilityClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import java.util.prefs.Preferences
import kotlin.math.abs

class ProfileFragment : MainPageFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startPickImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val uri = data?.data
                    if (uri != null) {
                        val inputStream = requireContext().contentResolver.openInputStream(uri)
                        var bitmap = BitmapFactory.decodeStream(inputStream)

                        bitmap = cropSquareBitmap(bitmap)
                        bitmap = cropCircleBitmap(bitmap)

                        ibAvatar.setImageBitmap(bitmap)
                        ImageUtilityClass.cropCenter(ibAvatar)

                        storeImage(uri.toString())
                    }
                }
                isChooseImage = false
            }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startPickImageResult.launch(intent)
            }
        }



    }

//    private lateinit var viewModel: ProfileViewModel

    private var isChooseImage = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    private lateinit var ibAvatar: ImageButton

    private var composeView: ComposeView? = null
    private var showDialog = mutableStateOf(false)
    private var currentEmail = mutableStateOf("")
    private lateinit var startPickImageResult: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var profilePreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilePreferences = requireContext().getSharedPreferences(
            USER_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

        profilePreferences.getString("email", null)?.also {
            currentEmail.value = it
        }

        profilePreferences.getString("username", null)?.also {
            val etUsername = view.findViewById<EditText>(R.id.etUsername)
            etUsername.setText(it)
        }



        composeView = view.findViewById(R.id.comvProfile)

        initComposeView(composeView)

        initWidgetSetUp(view)

        initChangeEmail(view)

        initClose(view)

        ibAvatar = view.findViewById(R.id.ibAvatar)

        loadImage(ibAvatar)

        pickImage(ibAvatar)
        val cvProfile = view.findViewById<CardView>(R.id.cvProfile)
        val etUsername = view.findViewById<EditText>(R.id.etUsername)

        // set on text change listener
        etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })

        // set unfocused when user click cvProfile
        cvProfile.setOnClickListener {
            clearFocusUsername(etUsername, it)
        }

    }

    // when pause or stop, we store the data
    override fun onPause() {
        super.onPause()
        storeData()
    }

    private fun storeData() {
        val etUsername = view?.findViewById<EditText>(R.id.etUsername)
        val username = etUsername?.text.toString()
        profilePreferences.edit().putString("username", username).apply()
        profilePreferences.edit().putString("email", currentEmail.value).apply()
    }

    private fun clearFocusUsername(etUsername: EditText, it: View) {
        // check if etUsername is empty
        if (etUsername.text.toString().isEmpty()) {
            //set hint
            etUsername.hint = "Enter your username"
            return;
        }
        etUsername.clearFocus()
        KeyboardUtilityClass.hideKeyboard(requireContext(), it)
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

    @SuppressLint("ObsoleteSdkInt")
    private fun pickImage(ibAvatar: ImageButton?) {
        ibAvatar?.setOnClickListener {
            if(isChooseImage) {
                return@setOnClickListener
            }

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    goToGallery()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                goToGallery()
            }
        }
    }

    private fun goToGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startPickImageResult.launch(intent)
        isChooseImage = true
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