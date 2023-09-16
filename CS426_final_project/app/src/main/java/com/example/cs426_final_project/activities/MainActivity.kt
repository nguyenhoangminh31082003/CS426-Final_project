package com.example.cs426_final_project.activities

// import view pager adapter

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cs426_final_project.R
import com.example.cs426_final_project.Singleton.UserLocation
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.MainPageContract
import com.example.cs426_final_project.contracts.PageTransformerContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.access.USER_PREFERENCES_NAME
import com.example.cs426_final_project.fragments.main.FeedsFragment
import com.example.cs426_final_project.fragments.main.HorizontalMainPageHolderFragment
import com.example.cs426_final_project.transformation.ZoomFadePageTransformer
import com.example.cs426_final_project.worker.UpdateWorker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), MainPageContract {

    private lateinit var vpVerticalMain: ViewPager2
    private lateinit var registerForSignInActivityResult: ActivityResultLauncher<Intent>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onStop() {
        super.onStop()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    private lateinit var requestPermissionLauncher : ActivityResultLauncher<Array<String>>

    private fun requestPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
            )
        )
    }


    private fun initTrackingUserLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val lastLocation = locationResult.lastLocation
                if (lastLocation != null) {
                    UserLocation.latitude = lastLocation.latitude
                    UserLocation.longitude = lastLocation.longitude
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            (
                    60 * 1000
                    ).toLong()
        ).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            var isAllGranted = true
            for ((permission, isGranted) in result) {
                if (!isGranted) {
                    isAllGranted = false
                }
            }
            if (isAllGranted) {

            } else {
                finish()
            }
        }

        registerForSignInActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // do nothing
            } else {
                finish()
            }
        }

        periodicCacheImages()
        initTrackingUserLocation()

        // fix vertical orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED


        initHorizontalViewPager()

        if (needSignIn()) {
            signIn()
        }

        requestPermission()
    }



    private fun periodicCacheImages() {
        val updateWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<UpdateWorker>(1, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(this).enqueue(updateWorkRequest);
    }

    private fun initHorizontalViewPager() {
        vpVerticalMain = findViewById(R.id.vpVerticalMain)

        // unable user scroll

        // set vertical instead of horizontal
        vpVerticalMain.orientation = ViewPager2.ORIENTATION_VERTICAL

        vpVerticalMain.offscreenPageLimit = 1
        vpVerticalMain.setPageTransformer(ZoomFadePageTransformer(object : PageTransformerContract {
            override fun onPageTransform(view: View, position: Float) {
                // do nothing
            }
        }))

        vpVerticalMain.adapter = ViewPagerAdapter(this, object : ViewPagerContract {
            override fun createFragment(position: Int): Fragment {
                return if(position == 0) {
                    HorizontalMainPageHolderFragment().also {
                        it.changeLockState = { unlock: Boolean ->
                            vpVerticalMain.isUserInputEnabled = unlock
                        }
                        it.onRequestToFeed = {
                            vpVerticalMain.setCurrentItem(1, true)
                        }
                        it.onLogout = {
                            clearLocalData()
                            signIn()
                        }
                    }

                } else FeedsFragment()
                    .also {
                    it.listener = FeedsFragment.OnFeedsFragmentListener { vpVerticalMain.setCurrentItem(0, true) }
                }
            }


            override fun getItemCount(): Int = 2
        })

    }

    private fun clearLocalData() {
        val profilePreferences: SharedPreferences =
            getSharedPreferences(USER_PREFERENCES_NAME, MODE_PRIVATE)
        profilePreferences.edit().clear().apply()
    }

    private fun signIn() {
        val signInIntent = Intent(this, SignInActivity::class.java)
        registerForSignInActivityResult.launch(signInIntent)
    }

    private fun needSignIn(): Boolean {
        return true
    }

    override fun setToMainPage(smoothScrolling: Boolean) {
        vpVerticalMain.setCurrentItem(1, smoothScrolling)

    }

    override fun foodScanToProfilePage() {
        vpVerticalMain.setCurrentItem(2, true)
    }

    override fun foodScanToMyFriendsPage() {
        vpVerticalMain.setCurrentItem(0, true)
    }

}