package com.example.cs426_final_project.models.viewmodel

import ProfileInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
//
//

//
//class ProfileViewModel(
//    val profileInfo: ProfileInfo,
//    private val profilePreferences: ProfilePreferences
//) : ViewModel() {
//    private val profilePreferencesFlow = profilePreferences.profilePreferencesFlow
//
//    init {
////        updateProfileInfo()
//    }
//
//    private val profileUiFlow = combine(
//        flowOf(profileInfo),
//        profilePreferencesFlow
//    ) { _, profilePreferencesFlow ->
//        ProfileUiModel(
//            name = profilePreferencesFlow.username,
//            email = profilePreferencesFlow.email,
//            avatar = profilePreferencesFlow.avatarUri,
//            userId = profilePreferencesFlow.userId
//        )
//    }
//    val profileUiModel = profileUiFlow.asLiveData()
//    private fun updateProfileInfo() {
//        viewModelScope.launch {
//            profilePreferences.updateProfileInfo(
//                userId = profileInfo.userId,
//                username = profileInfo.username,
//                avatarUri = profileInfo.avatarUri,
//                email = profileInfo.email
//            )
//        }
//    }
//
//    fun printCurrentProfileInfo() {
//        viewModelScope.launch {
//            val profileInfo = profilePreferences.readProfileInfo()
//            println("Profile Info from viewModel: ${profileInfo.userId} ${profileInfo.username} ${profileInfo.avatarUri} ${profileInfo.email}")
//        }
//    }
//
//
//
//}
//
//class ProfileViewModelFactory(
//    private val profileInfo: ProfileInfo,
//    private val profilePreferences: ProfilePreferences
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ProfileViewModel(
//                profilePreferences = profilePreferences,
//                profileInfo = profileInfo
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//
//
//
//}
