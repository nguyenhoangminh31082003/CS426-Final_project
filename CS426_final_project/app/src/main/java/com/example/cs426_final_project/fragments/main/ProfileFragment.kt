package com.example.cs426_final_project.fragments.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import com.example.cs426_final_project.R
import com.example.cs426_final_project.api.UsersApi
import com.example.cs426_final_project.fragments.access.USER_PREFERENCES_NAME
import com.example.cs426_final_project.models.data.ProfileDataModel
import com.example.cs426_final_project.models.response.ProfileResponse
import com.example.cs426_final_project.models.response.StatusResponse
//import com.example.cs426_final_project.models.viewmodel.ProfileViewModelFactory
import com.example.cs426_final_project.notifications.CustomDialog
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.utilities.ImageUtilityClass
import com.example.cs426_final_project.utilities.KeyboardUtilityClass
import com.example.cs426_final_project.utilities.WidgetUtilityClass
import com.example.cs426_final_project.utilities.ApiUtilityClass
import kotlin.math.abs

class ProfileFragment : MainPageFragment() {

    private lateinit var profileFragmentContract: ProfileFragmentContract
    private var profileDataModel  = ProfileDataModel("", "", "", "", "")
    private var loadDataFromServerOnResume = true
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

                        bitmap = transformBitmapForAvatar(bitmap)

                        ibAvatar.setImageBitmap(bitmap)
                        ImageUtilityClass.cropCenter(ibAvatar)

                        updateImageProfileDataModel(bitmap)
                    }

                }
                isChooseImage = false
                loadDataFromServerOnResume = false
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


    private fun transformBitmapForAvatar(bitmap: Bitmap?): Bitmap? {
        var bitmap1 = bitmap
        bitmap1 = ImageUtilityClass.cropSquareBitmap(bitmap1)
        bitmap1 = ImageUtilityClass.cropCircleBitmap(bitmap1)
        return bitmap1
    }

    private fun updateImageProfileDataModel(bitmap: Bitmap?) {
        if (bitmap != null) {
            profileDataModel.avatarBase64 = ImageUtilityClass.bitmapToBase64(bitmap)
            profileDataModel.avatarFilename = profileDataModel.name + ".png"
        }
    }

    private var isChooseImage = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    private lateinit var ibAvatar: ImageButton
    private lateinit var etUsername: EditText
    private lateinit var cvProfile: CardView
    private lateinit var btnChangeEmail: Button
    private lateinit var ibClose: ImageButton
    private lateinit var btnAddWidget: Button
    private lateinit var btnLogout : Button

    private var composeView: ComposeView? = null
    private lateinit var showDialog : MutableState<Boolean>
    private lateinit var currentEmail :MutableState<String>
    private lateinit var startPickImageResult: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var profilePreferences: SharedPreferences

    private lateinit var currentProfile : ProfileResponse


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentEmail = mutableStateOf("")

        showDialog = mutableStateOf(false)

        ibAvatar = view.findViewById(R.id.ibAvatar)
        cvProfile = view.findViewById(R.id.cvProfile)
        etUsername = view.findViewById(R.id.etUsername)
        btnChangeEmail = view.findViewById(R.id.btnChangeEmail)
        ibClose = view.findViewById(R.id.ibClose)
        btnAddWidget = view.findViewById(R.id.btnAddWidget)
        btnLogout = view.findViewById(R.id.btnLogout)
//        loadFromServer()

//        composeView = view.findViewById(R.id.comvProfile)
//
//        initComposeView(composeView)

        initWidgetSetUp(view)
        initChangeEmail()
        initClose()
        pickImage(ibAvatar)
        // set unfocused when user click cvProfile
        cvProfile.setOnClickListener {
            clearFocusUsername(etUsername, it)
        }

        btnLogout.setOnClickListener {
            logout()
        }

        lockUserInput(true)
        loadFromServer(requireContext())
    }

    private fun logout() {
        val usersApi = ApiUtilityClass.getApiClient(requireContext()).create(UsersApi::class.java)
        val call = usersApi.userLogout()
        call.enqueue(object : retrofit2.Callback<StatusResponse> {
            override fun onResponse(
                call: retrofit2.Call<StatusResponse>,
                response: retrofit2.Response<StatusResponse>
            ) {
                if (response.isSuccessful) {
                    println("logout: ${response.body()}")
                    val status = response.body()
                    if (status != null) {
                        if(status.status == "You are now logged out") {
                            profileFragmentContract.logout()
                        }
                    }
                } else {
                    ApiUtilityClass.debug(response)
                }
            }

            override fun onFailure(call: retrofit2.Call<StatusResponse>, t: Throwable) {
                print("Oh no! Something went wrong in register view model ${t.message}")
            }
        })
    }

    private fun loadLocalData() {
        profilePreferences = requireContext().getSharedPreferences(
            USER_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

        profilePreferences.getString("email", "")?.also {
            currentEmail.value = it
        }

        profilePreferences.getString("username", "")?.also {
            etUsername.setText(it)
        }
    }

    private fun loadFromServer(context: Context) {
        val usersApi = ApiUtilityClass.getApiClient(requireContext()).create(UsersApi::class.java)
        val call = usersApi.getLoggedProfile()
        call.enqueue(object : retrofit2.Callback<ProfileResponse> {
            override fun onResponse(
                call: retrofit2.Call<ProfileResponse>,
                response: retrofit2.Response<ProfileResponse>
            ) {
                if (response.isSuccessful) {
                    println("loadFromServer: ${response.body()}")
                    val profile = response.body()
                    if (profile != null) {
                        currentProfile = profile
                        syncData(context, profile)
                    }
                } else {
                    ApiUtilityClass.debug(response)
//                    loadLocalData()
                }

            }

            override fun onFailure(call: retrofit2.Call<ProfileResponse>, t: Throwable) {
                print("Oh no! Something went wrong in register view model ${t.message}")
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun lockUserInput(b: Boolean) {

        try {
            etUsername.isEnabled = !b
            btnChangeEmail.isEnabled = !b
            btnAddWidget.isEnabled = !b
            btnLogout.isEnabled = !b
            // set text color
            if (b) {
                etUsername.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                etUsername.clearFocus()
                etUsername.setText("")

            } else {
                etUsername.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        } catch (e: Exception) {
            print("lockUserInput: ${e.message}")
        }


    }

    private fun syncData(context: Context, profile: ProfileResponse) {

        etUsername.setText(profile.fullName)
        currentEmail.value = ""
        profileDataModel.name = profile.fullName
        val url = profile.avatar
        profileDataModel.bio = profile.bio
        profileDataModel.avatar = url
        // use regex to get the last part of the url avatar
        val regex = Regex("([^/]+$)")
        val match = regex.find(url)
        if (match != null) {
            profileDataModel.avatarFilename = match.value
            try {
                ImageUtilityClass.loadBitmapFromUrl(context, url, callback = {
                    val bitmap = transformBitmapForAvatar(it)
                    if(bitmap != null) {
                        profileDataModel.avatarBase64 = ImageUtilityClass.bitmapToBase64(bitmap)
                        ibAvatar.setImageBitmap(bitmap)
                    }
                })
            } catch (e: Exception) {
                print("cannot load bitmap from url: ${e.message}")
            }

        }
        lockUserInput(false)
    }

    // when pause or stop, we store the data
    override fun onPause() {
        super.onPause()
        if(isChooseImage) {
            return
        }
        storeData()
    }

    private fun storeData() {
        storeToServer()
    }

    private fun storeToServer() {

        if(etUsername.text.toString() != ""){
            profileDataModel.name = etUsername.text.toString()
        }
        profileDataModel.avatarFilename = profileDataModel.name + ".png"

        val usersApi = ApiUtilityClass.getApiClient(requireContext()).create(UsersApi::class.java)
        val call = usersApi.updateProfile(profileDataModel)
        call.enqueue(object : retrofit2.Callback<StatusResponse> {
            override fun onResponse(
                call: retrofit2.Call<StatusResponse>,
                response: retrofit2.Response<StatusResponse>
            ) {
                if (response.isSuccessful) {
                    println("storeToServer: ${response.body()}")
                } else {
                    ApiUtilityClass.debug(response)
                }
            }

            override fun onFailure(call: retrofit2.Call<StatusResponse>, t: Throwable) {
                print("Oh no! Something went wrong in register view model ${t.message}")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if(loadDataFromServerOnResume) {
            lockUserInput(true)
            loadFromServer(requireContext())
        }
        loadDataFromServerOnResume = true
    }

    private fun storeLocalData(username: String) {
        profilePreferences = requireContext().getSharedPreferences(
            USER_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
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

    private fun initClose() {

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

    private fun initChangeEmail() {

//        btnChangeEmail.setOnClickListener {
//            showDialog.value = true
//        }

    }

    private fun initWidgetSetUp(view: View) {
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

        interface ProfileFragmentContract {
            fun logout()
        }

        fun newInstance(profileFragmentContract: ProfileFragmentContract): ProfileFragment {
            return ProfileFragment().also {
                it.profileFragmentContract = profileFragmentContract
            }
        }
    }
}