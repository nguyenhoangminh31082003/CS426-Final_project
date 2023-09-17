package com.example.cs426_final_project.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cs426_final_project.api.UsersApi
import com.example.cs426_final_project.R
import com.example.cs426_final_project.adapters.ViewPagerAdapter
import com.example.cs426_final_project.contracts.LoginContract
import com.example.cs426_final_project.contracts.ViewPagerContract
import com.example.cs426_final_project.fragments.access.RegisterFragment.RegisterContract
import com.example.cs426_final_project.fragments.access.USER_PREFERENCES_NAME
import com.example.cs426_final_project.fragments.access.WelcomeFragment
import com.example.cs426_final_project.fragments.access.WelcomeFragment.WelcomeContract
import com.example.cs426_final_project.fragments.access.newInstance
import com.example.cs426_final_project.models.data.LoginDataModel
import com.example.cs426_final_project.models.data.ProfileDataModel
import com.example.cs426_final_project.models.response.ProfileResponse
import com.example.cs426_final_project.models.response.StatusResponse
import com.example.cs426_final_project.utilities.api.ApiUtilityClass
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
                    fragment = newInstance(object : LoginContract {
                        override fun login() {
                            vpSignIn!!.setCurrentItem(EnumPage.LOGIN, true)
                        }

                        override fun returnToWelcome() {
                            vpSignIn!!.setCurrentItem(EnumPage.WELCOME, true)
                        }

                        override fun onConfirm(email : String, password : String) {
                            callApiLogin(email, password)
                        }
                    })
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
                processLoginRequest()
            }

            override fun register() {
                vpSignIn!!.setCurrentItem(EnumPage.REGISTER, true)
            }

        })
    }



    private fun callApiLogin(email: String, password: String) {
        val apiService: UsersApi = ApiUtilityClass.getApiClient(this).create(UsersApi::class.java)

        val call = apiService.userLogin(LoginDataModel(email, password))

        call.enqueue(object : retrofit2.Callback<StatusResponse> {
            override fun onResponse(
                call: retrofit2.Call<StatusResponse>,
                response: retrofit2.Response<StatusResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    println("login response: $loginResponse")
                    if (loginResponse != null) {
                        val intent = Intent()
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                } else {
                    ApiUtilityClass.debug(response)
                }
            }

            override fun onFailure(call: retrofit2.Call<StatusResponse>, t: Throwable) {
                print("Oh no! Something went wrong in register view model ${t.message}")
            }
        })
    }

    private fun processLoginRequest() {
        val usersApi = ApiUtilityClass.getApiClient(this).create(UsersApi::class.java)
        val call = usersApi.getLoggedProfile()
        call.enqueue(object : retrofit2.Callback<ProfileResponse> {
            override fun onResponse(
                call: retrofit2.Call<ProfileResponse>,
                response: retrofit2.Response<ProfileResponse>
            ) {
                if (response.isSuccessful ) {
                    val profile = response.body()
                    if (profile != null) {
                        storeResponseProfile(profile)
                        onLogged()
                    }

                } else {
                    vpSignIn!!.setCurrentItem(EnumPage.LOGIN, true)
                    ApiUtilityClass.debug(response)
                }
            }

            override fun onFailure(call: retrofit2.Call<ProfileResponse>, t: Throwable) {
                println("failed to get profile")
            }
        })
    }

    @SuppressLint("ApplySharedPref")
    private fun storeResponseProfile(profile: ProfileResponse) {
        val sharedPreferences = getSharedPreferences(
            USER_PREFERENCES_NAME,
            MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString("avatar", profile.avatar)
        editor.putString("name", profile.fullName)
        editor.putString("bio", profile.bio)
        editor.commit()
    }

    private fun onLogged() {
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

}