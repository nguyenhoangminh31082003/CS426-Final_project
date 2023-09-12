package com.example.cs426_final_project.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cs426_final_project.API.UsersApi
import com.example.cs426_final_project.R
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.LoginContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.access.RegisterFragment.RegisterContract
import com.example.cs426_final_project.fragments.access.WelcomeFragment
import com.example.cs426_final_project.fragments.access.WelcomeFragment.WelcomeContract
import com.example.cs426_final_project.fragments.access.newInstance
import com.example.cs426_final_project.fragments.main.ProfileFragment
import com.example.cs426_final_project.models.User.LoginResponse
import com.example.cs426_final_project.utilities.ApiUtilityClass
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


internal class SignInActivity : AppCompatActivity() {
    object EnumPage {
        const val WELCOME = 1
        const val REGISTER = 0
        const val LOGIN = 2
    }


    var adapter: ViewPagerAdapter? = null
    var vpSignIn: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_sign_in_page)
        setUpViewPager()
    }

    private fun setUpViewPager() {

        vpSignIn = findViewById(R.id.vpSignIn)
        setUpAdapter()
        vpSignIn?.setCurrentItem(1, false)
        vpSignIn?.offscreenPageLimit = 1
        vpSignIn?.isUserInputEnabled = false

    }


    private fun setUpAdapter() {
        adapter = ViewPagerAdapter(this, object : ViewPagerContract {
            override fun createFragment(position: Int): Fragment {
                var fragment: Fragment? = null
                if (position == EnumPage.WELCOME) fragment = createWelcomeFragment()
                if (position == EnumPage.LOGIN) {
                    fragment = newInstance(loginContract)
                }
                if (position == EnumPage.REGISTER) {
                    fragment = createRegisterFragment()
                }
                return fragment!!
            }


            override fun getItemCount(): Int {
                return 3
            }
        })
        vpSignIn!!.adapter = adapter
    }

    private fun createRegisterFragment(): Fragment {
        return newInstance(object : RegisterContract {
            override fun onSuccessRegister() {
                vpSignIn!!.setCurrentItem(EnumPage.LOGIN, true)
            }

            override fun onUnSuccessRegister() {
                vpSignIn!!.setCurrentItem(EnumPage.WELCOME, true)
            }
        })
    }

    private fun createWelcomeFragment(): WelcomeFragment {
        return WelcomeFragment.newInstance(object : WelcomeContract {
            override fun login() {
                vpSignIn!!.setCurrentItem(EnumPage.LOGIN, true)
            }

            override fun register() {
                vpSignIn!!.setCurrentItem(EnumPage.REGISTER, true)
            }

            override fun onLogged() {
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    private val loginContract: LoginContract
        get() = object : LoginContract {
            override fun login() {
                vpSignIn!!.setCurrentItem(EnumPage.LOGIN, true)
            }

            override fun returnToWelcome() {
//                println("returnToWelcome")
                vpSignIn!!.setCurrentItem(EnumPage.WELCOME, true)
            }

            override fun onConfirm(email : String, password : String) {
                // use retrofit to login
                callApiLogin(email, password)

            }
        }

    private fun callApiLogin(email: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiUtilityClass.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: UsersApi = retrofit.create(UsersApi::class.java)
        val call = apiService.userLogin(email, password)

        call.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>,
                response: retrofit2.Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val intent = Intent()
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                } else {
                    println("Oh no, oh no, Error code: ${response.code()}")
                    println("Oh no, oh no, Error body: ${response.body()}")
                    println("Oh no, oh no, Error errorBody: ${response.errorBody()}")
                    println("Oh no, oh no, Error message: ${response.message()}")

                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                // throw message from server return
                print("Oh no! Something went wrong in register view model ${t.message}")
            }
        })


    }
}