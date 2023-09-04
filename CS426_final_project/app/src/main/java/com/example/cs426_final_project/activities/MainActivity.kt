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
import com.example.cs426_final_project.transformation.PageTransformer
import com.example.cs426_final_project.utilities.PagerUtilityClass

class MainActivity : AppCompatActivity(), ViewPagerContract, MainPageContract, PageTransformerContract {

    private lateinit var vpMain: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var pagerUtils: PagerUtilityClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        vpMain = findViewById(R.id.vpMain)
        vpMain.setOffscreenPageLimit(1)
        viewPagerAdapter = ViewPagerAdapter(this, this)
        vpMain.adapter = viewPagerAdapter
        setToMainPage()

        val pageIds = arrayOf(R.id.rootViewFriends, R.id.rootViewFoodScan, R.id.rootViewProfile)
        pagerUtils = PagerUtilityClass(pageIds.toList())

        // set page transformer
        vpMain.setPageTransformer(PageTransformer(this))

        // register vpMain to listen to page change
        vpMain.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                val progress = (position + positionOffset) / (viewPagerAdapter.itemCount - 1).toFloat()
//                print("progress: $progress, position: $position, positionOffset: $positionOffset\n")
//                val direction = (progress - 0.5) / 0.5f
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        if(needSignIn()) {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
    }

    private fun needSignIn(): Boolean {
        return false
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment
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
        vpMain.setCurrentItem(1, true)


    }

    override fun foodScanToProfilePage() {
        vpMain.setCurrentItem(2, true)
    }

    override fun foodScanToMyFriendsPage() {
        vpMain.setCurrentItem(0, true)
    }

    override fun onPageTransform(view: View, position: Float) {
        val directionInfo = pagerUtils.getDirectionOnSwiping(view, position, vpMain.currentItem)
        val relDisplacement = position
        val direction = directionInfo.direction
//        val swipingState = directionInfo.swipingState
//        print("direction from onPageTransform: $direction\n")
//        pagerUtils.debugSwiping(swipingState, vpMain.currentItem)

        ProfileFragment.updateFragmentTransform(view, position, direction,relDisplacement)
        MyFriendsFragment.updateFragmentTransform(view, position, direction,relDisplacement)
    }
}