package com.example.estore.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserStorage @Inject constructor(val context: Context) {
    companion object {
        const val SHARED_PREF_FILE = "SharedPreferencesEStore"
        const val SHARED_PREF_TOKEN_KEY = "SharedPreferencesTokenId"
        const val IS_FIRST_TIME = "IsFirstTime"
    }

    private val sharePref: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

    fun getActiveToken(): String? {
        return sharePref.getString(SHARED_PREF_TOKEN_KEY, null)
    }

    fun setActiveToken(token: String) {
        sharePref.edit {
            putString(SHARED_PREF_TOKEN_KEY, token)
        }
    }

    fun clearActiveToken() {
        sharePref.edit {
            remove(SHARED_PREF_TOKEN_KEY)
        }
    }

    var isFirstTime: Boolean
        get() = sharePref.getBoolean(IS_FIRST_TIME, true)
        set(value) = sharePref.edit { putBoolean(IS_FIRST_TIME, value) }

}