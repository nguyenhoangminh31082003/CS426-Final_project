package com.example.cs426_final_project.activities

// import view pager adapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cs426_final_project.R
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.MainPageContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.main.FoodScanFragment
import com.example.cs426_final_project.fragments.main.MyFriendsFragment
import com.example.cs426_final_project.fragments.main.ProfileFragment

class MainActivity : AppCompatActivity(), ViewPagerContract, MainPageContract {

    private lateinit var vpMain: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        vpMain = findViewById<ViewPager2>(R.id.vpMain)
        vpMain.adapter = ViewPagerAdapter(this, this)
        setToMainPage()

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
        vpMain.currentItem = 1
    }

    override fun foodScanToProfilePage() {
        vpMain.currentItem = 2
    }

    override fun foodScanToMyFriendsPage() {
        vpMain.currentItem = 0
    }
}