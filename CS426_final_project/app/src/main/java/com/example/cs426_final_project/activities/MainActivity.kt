package com.example.cs426_final_project.activities

// import view pager adapter
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cs426_final_project.R
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.CameraContract
import com.example.cs426_final_project.contracts.MainPageContract
import com.example.cs426_final_project.contracts.PageTransformerContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.main.FoodScanFragment
import com.example.cs426_final_project.fragments.main.MyFriendsFragment
import com.example.cs426_final_project.fragments.main.ProfileFragment
import com.example.cs426_final_project.transformation.ZoomFadePageTransformer
import com.example.cs426_final_project.utilities.PagerUtilityClass
import java.io.OutputStream
import java.util.Objects

class MainActivity : AppCompatActivity(), MainPageContract {

    private lateinit var vpHorizontalMain: ViewPager2
    private lateinit var pagerUtils: PagerUtilityClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initHorizontalViewPager()

        if(needSignIn()) {
            signIn()
        }

    }

    private fun initHorizontalViewPager() {
        vpHorizontalMain = findViewById(R.id.vpHorzMain)

        //  this is not the type of ViewPagerContract
        val viewPagerAdapter = ViewPagerAdapter(this, object : ViewPagerContract {
            override fun createFragment(position: Int): Fragment {
                val fragment: Fragment = when(position) {
                    0 -> MyFriendsFragment()
                    1 -> FoodScanFragment()
                    2 -> ProfileFragment()
                    else -> FoodScanFragment()
                }
                return fragment
            }

            override fun getItemCount(): Int = 3
        })


        val pageIds = arrayOf(R.id.rootViewFriends, R.id.rootViewFoodScan, R.id.rootViewProfile)

        vpHorizontalMain.adapter = viewPagerAdapter
        vpHorizontalMain.offscreenPageLimit = 1


        pagerUtils = PagerUtilityClass(pageIds.toList())

        vpHorizontalMain.setPageTransformer(ZoomFadePageTransformer(object : PageTransformerContract{
            override fun onPageTransform(view: View, position: Float) {
                val directionInfo = pagerUtils.getDirectionOnSwiping(view, position, vpHorizontalMain.currentItem)
                val relDisplacement = position
                val direction = directionInfo.direction
//        val swipingState = directionInfo.swipingState
//        print("direction from onPageTransform: $direction\n")
//        pagerUtils.debugSwiping(swipingState, vpMain.currentItem)

                ProfileFragment.updateFragmentTransform(view, position, direction,relDisplacement)
                MyFriendsFragment.updateFragmentTransform(view, position, direction,relDisplacement)
            }

        }))

        setToMainPage()
    }

    private fun signIn() {
        val signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
    }

    private fun needSignIn(): Boolean {
        return false
    }

    override fun setToMainPage() {
        vpHorizontalMain.setCurrentItem(1, true)

    }

    override fun foodScanToProfilePage() {
        vpHorizontalMain.setCurrentItem(2, true)
    }

    override fun foodScanToMyFriendsPage() {
        vpHorizontalMain.setCurrentItem(0, true)
    }

}