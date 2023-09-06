package com.example.cs426_final_project.fragments.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.os.Bundle
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
import com.example.cs426_final_project.R
import com.example.cs426_final_project.notifications.CustomDialog
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.utilities.ImageUtilityClass
import com.example.cs426_final_project.utilities.WidgetUtilityClass
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

                        // crop bitmap to have circle shape
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

    private var isChooseImage = false

    private fun cropSquareBitmap(originalBitmap: Bitmap?): Bitmap? {

// Calculate the size of the square

// Calculate the size of the square
        val size = originalBitmap?.let { it.width.coerceAtMost(originalBitmap.height) }

// Create a square bitmap

// Create a square bitmap
        val squareBitmap = size?.let { Bitmap.createBitmap(it, size, Bitmap.Config.ARGB_8888) }

// Create a canvas with the square bitmap

// Create a canvas with the square bitmap
        val canvas = squareBitmap?.let { Canvas(it) }

// Calculate the coordinates to crop the center of the original bitmap

// Calculate the coordinates to crop the center of the original bitmap
        val left = (originalBitmap!!.width - size!!) / 2
        val top = (originalBitmap.height - size) / 2

// Copy the square portion from the original bitmap to the square bitmap

// Copy the square portion from the original bitmap to the square bitmap
        val srcRect = Rect(left, top, left + size, top + size)
        val destRect = Rect(0, 0, size, size)
        canvas?.drawBitmap(originalBitmap, srcRect, destRect, null)

        print("squareBitmap: ${squareBitmap?.width}, ${squareBitmap?.height}\n")


        return squareBitmap
    }

    private fun cropCircleBitmap(originalBitmap: Bitmap?): Bitmap? {
        val circularBitmap = Bitmap.createBitmap(
            originalBitmap!!.width,
            originalBitmap.height,
            Bitmap.Config.ARGB_8888
        )

// Create a canvas with the circular bitmap

// Create a canvas with the circular bitmap
        val canvas = Canvas(circularBitmap)

// Create a paint object for drawing

// Create a paint object for drawing
        val paint = Paint()
        paint.shader = BitmapShader(
            originalBitmap,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )

// Calculate the radius for the circle

// Calculate the radius for the circle
        val radius = Math.min(originalBitmap.width, originalBitmap.height) / 2

// Draw a circle with the image as the shader

// Draw a circle with the image as the shader
        canvas.drawCircle(
            (originalBitmap.width / 2).toFloat(),
            (originalBitmap.height / 2).toFloat(), radius.toFloat(), paint
        )

        return circularBitmap
    }



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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    @SuppressLint("ObsoleteSdkInt")
    private fun pickImage(ibAvatar: ImageButton?) {
        ibAvatar?.setOnClickListener {
            if(isChooseImage) {
                return@setOnClickListener
            }
//            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            // check if version is greater than 23 then we need to ask for runtime permission
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
                // check if permission is given
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    // if permission granted then go to gallery
                    goToGallery()
                } else {
                    // if permission not granted ask for permission
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