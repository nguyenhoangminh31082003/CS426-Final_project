package com.example.cs426_final_project.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.cs426_final_project.fragments.main.ProfileFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class ProfilePreferences(
    private val dataStore: DataStore<Preferences>,
) {

    val profilePreferencesFlow: Flow<ProfileInfo> = dataStore.data
        .catch {exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            ProfileInfo(
                it[ProfileFragment.PreferencesKeys.USER_ID] ?: "",
                it[ProfileFragment.PreferencesKeys.USERNAME] ?: "",
                it[ProfileFragment.PreferencesKeys.AVATAR_URI] ?: "",
                it[ProfileFragment.PreferencesKeys.EMAIL] ?: "",
            )
        }

    suspend fun updateProfileInfo(userId : String, username : String, avatarUri : String, email : String) {
        dataStore.edit { preferences ->
            preferences[ProfileFragment.PreferencesKeys.USER_ID] = userId
            preferences[ProfileFragment.PreferencesKeys.USERNAME] = username
            preferences[ProfileFragment.PreferencesKeys.AVATAR_URI] = avatarUri
            preferences[ProfileFragment.PreferencesKeys.EMAIL] = email
        }
    }

}

data class ProfileInfo (
    var userId : String,
    val username : String,
    val avatarUri : String,
    val email : String,
)