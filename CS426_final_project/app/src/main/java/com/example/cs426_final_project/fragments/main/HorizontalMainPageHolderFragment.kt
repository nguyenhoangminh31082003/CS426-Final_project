package com.example.cs426_final_project.fragments.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cs426_final_project.R
import com.example.cs426_final_project.activities.SignInActivity
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.MainPageContract
import com.example.cs426_final_project.contracts.PageTransformerContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.access.USER_PREFERENCES_NAME
import com.example.cs426_final_project.transformation.ZoomFadePageTransformer
import com.example.cs426_final_project.utilities.KeyboardUtilityClass
import com.example.cs426_final_project.utilities.PagerUtilityClass

class HorizontalMainPageHolderFragment : Fragment() {


    lateinit var onLogout: () -> Unit
    private lateinit var vpHorizontalMain: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_horizontal_main_page_holder, container, false)
    }



    var changeLockState = { _: Boolean -> }
    var onRequestToFeed = { _: Unit -> }

    private val mainPageContract: MainPageContract = object : MainPageContract {
        override fun setToMainPage(smoothScrolling: Boolean) =
            vpHorizontalMain.setCurrentItem(1, smoothScrolling)

        override fun foodScanToProfilePage() =
            vpHorizontalMain.setCurrentItem(2, true)

        override fun foodScanToMyFriendsPage() =
            vpHorizontalMain.setCurrentItem(0, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHorizontalViewPager(view)
        mainPageContract.setToMainPage(smoothScrolling = false)
        vpHorizontalMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                KeyboardUtilityClass.hideKeyboard(requireContext(), view)
            }
        })
    }

    private fun initHorizontalViewPager(view: View) {
        vpHorizontalMain = view.findViewById(R.id.vpHorizontalMain)
        setUpAdapter()
        addTransformation()

        vpHorizontalMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                changeLockState(position == 1)
            }
        })
    }

    private fun setUpAdapter() {
        vpHorizontalMain.adapter = ViewPagerAdapter(requireActivity(), object : ViewPagerContract {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> MyFriendsFragment()
                    1 -> FoodScanFragment().also {
                        it.listener = FoodScanFragment.OnFoodScanFragmentListener {
                            onRequestToFeed(Unit)
                        }
                    }
                    2 -> ProfileFragment.newInstance(object : ProfileFragment.Companion.ProfileFragmentContract {
                        override fun logout() {
                            vpHorizontalMain.setCurrentItem(1, false)
                            onLogout()
                        }
                    })
                    else -> FoodScanFragment()
                }.also {
                    it.mainPageContract = mainPageContract
                }
            }

            override fun getItemCount(): Int = 3
        })

        // hide keyboard when swiping

    }

    private fun addTransformation() {
        val pagerUtils = PagerUtilityClass(
            arrayOf(
                R.id.rootViewFriends,
                R.id.rootViewFoodScan,
                R.id.rootViewProfile
            ).toList()
        )

        vpHorizontalMain.offscreenPageLimit = 1

        vpHorizontalMain.setPageTransformer(ZoomFadePageTransformer(object :
            PageTransformerContract {
            override fun onPageTransform(view: View, position: Float) {
                val directionInfo =
                    pagerUtils.getDirectionOnSwiping(view, position, vpHorizontalMain.currentItem)
                val relDisplacement = position
                val direction = directionInfo.direction
                //                val swipingState = directionInfo.swipingState
                //                print("direction from onPageTransform: $direction\n")
                //                pagerUtils.debugSwiping(swipingState, vpMain.currentItem)

                ProfileFragment.updateFragmentTransform(view, position, direction, relDisplacement)
                MyFriendsFragment.updateFragmentTransform(
                    view,
                    position,
                    direction,
                    relDisplacement
                )
            }
        }))
    }
}