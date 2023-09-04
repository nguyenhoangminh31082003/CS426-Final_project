package com.example.cs426_final_project.activities

// import view pager adapter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cs426_final_project.R
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.MainPageContract
import com.example.cs426_final_project.contracts.PageTransformerContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.main.HorizontalMainPageHolderFragment
import com.example.cs426_final_project.transformation.PageTransformer
import com.example.cs426_final_project.transformation.ZoomFadePageTransformer
import com.example.cs426_final_project.utilities.PagerUtilityClass
import java.lang.Math.abs

class MainActivity : AppCompatActivity(), MainPageContract {

    private lateinit var vpVerticalMain: ViewPager2
    private lateinit var pagerUtils: PagerUtilityClass


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fix vertical orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        initHorizontalViewPager()

        if(needSignIn()) {
            signIn()
        }

    }

    private fun initHorizontalViewPager() {
        vpVerticalMain = findViewById(R.id.vpVerticalMain)

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
                    }

                } else FeedsFragment().also {
                    it.listener = FeedsFragment.OnFeedsFragmentListener { vpVerticalMain.setCurrentItem(0, true) }
                }
            }


            override fun getItemCount(): Int = 2
        })


    }

    private fun signIn() {
        val signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
    }

    private fun needSignIn(): Boolean {
        return false
    }

    override fun setToMainPage() {
        vpVerticalMain.setCurrentItem(1, true)

    }

    override fun foodScanToProfilePage() {
        vpVerticalMain.setCurrentItem(2, true)
    }

    override fun foodScanToMyFriendsPage() {
        vpVerticalMain.setCurrentItem(0, true)
    }

}