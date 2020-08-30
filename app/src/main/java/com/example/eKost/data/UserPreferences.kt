package com.example.eKost.data

import android.content.Context
import android.content.SharedPreferences
import com.example.eKost.data.model.User

class UserPreferences(context: Context) {

    private val PREFS_NAME = "user_pref"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"
    private val KEY_LOGIN = "loginstate"
    private val KEY_CODE = "code"

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setUser(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, user.email)
        editor.putString(KEY_PASSWORD, user.password)
        editor.putBoolean(KEY_LOGIN, user.isValid)
        editor.apply()
    }

    fun setLoginCode(code: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CODE, code)
        editor.apply()
    }

//    fun getLoginCode(): Int? = sharedPreferences.getInt(KEY_CODE, 0)

    fun getUser(): User {
        val user = User()
        user.email = sharedPreferences.getString(KEY_USERNAME, null)
        user.password = sharedPreferences.getString(KEY_PASSWORD, null)
        user.isValid = sharedPreferences.getBoolean(KEY_LOGIN, false)
        return user
    }

//    fun removeLoginCode() = sharedPreferences.edit().remove(KEY_CODE).apply()

    fun removeUser() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_PASSWORD)
        editor.remove(KEY_LOGIN)
        editor.apply()
    }
}