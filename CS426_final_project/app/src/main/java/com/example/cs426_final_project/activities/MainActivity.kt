package com.example.cs426_final_project.activities

// import view pager adapter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.work.PeriodicWorkRequest
import com.example.cs426_final_project.R
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.MainPageContract
import com.example.cs426_final_project.contracts.PageTransformerContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.access.USER_PREFERENCES_NAME
import com.example.cs426_final_project.fragments.main.FeedsFragment
import com.example.cs426_final_project.fragments.main.HorizontalMainPageHolderFragment
import com.example.cs426_final_project.transformation.ZoomFadePageTransformer
import com.example.cs426_final_project.worker.UpdateWorker
import java.util.concurrent.TimeUnit

import androidx.work.PeriodicWorkRequestBuilder;
import androidx.work.WorkManager


class MainActivity : AppCompatActivity(), MainPageContract {

    private lateinit var vpVerticalMain: ViewPager2
    private lateinit var registerForActivityResult: ActivityResultLauncher<Intent>

    // Register intent result


    private fun requestPermissions() {
        
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 1
            )
        }

        // ask list of permission

    }
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()

        periodicCacheImages()

        // fix vertical orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode != RESULT_OK) {
                    finish()
                }
            }


        initHorizontalViewPager()

        if(needSignIn()) {
            signIn()
        }
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
        registerForActivityResult.launch(signInIntent)
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