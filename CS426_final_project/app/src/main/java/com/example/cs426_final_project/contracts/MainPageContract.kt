package com.example.cs426_final_project.contracts

interface MainPageContract {
    fun setToMainPage(smoothScrolling : Boolean = false)
    fun foodScanToProfilePage()
    fun foodScanToMyFriendsPage()

}