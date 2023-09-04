package com.example.cs426_final_project.activities

// import view pager adapter
import android.content.Intent
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
import com.example.cs426_final_project.fragments.main.FoodScanFragment
import com.example.cs426_final_project.fragments.main.MyFriendsFragment
import com.example.cs426_final_project.fragments.main.ProfileFragment
import com.example.cs426_final_project.transformation.ZoomFadePageTransformer
import com.example.cs426_final_project.utilities.PagerUtilityClass

class MainActivity : AppCompatActivity(), ViewPagerContract, MainPageContract, PageTransformerContract {

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

        val viewPagerAdapter = ViewPagerAdapter(this, this)
        val pageIds = arrayOf(R.id.rootViewFriends, R.id.rootViewFoodScan, R.id.rootViewProfile)

        vpHorizontalMain.adapter = viewPagerAdapter
        vpHorizontalMain.offscreenPageLimit = 1


        pagerUtils = PagerUtilityClass(pageIds.toList())

        vpHorizontalMain.setPageTransformer(ZoomFadePageTransformer(this))

        setToMainPage()
    }

    private fun signIn() {
        val signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
    }

    private fun needSignIn(): Boolean {
        return false
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment
        when(position) {
            0 -> {
                fragment =
                    MyFriendsFragment()
            }
            1 -> {
                fragment =
                    FoodScanFragment()
            }
            2 -> {
                fragment = ProfileFragment()
            }
            else -> {
                fragment =
                    FoodScanFragment()
            }
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 3
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
}