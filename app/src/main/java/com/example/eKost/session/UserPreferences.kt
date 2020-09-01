package com.example.eKost.session

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val PREFS_NAME = "user_pref"
    private val KEY_USERID = "userid"
    private val KEY_USERNAME = "username"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_ADDRESS = "address"
    private val KEY_NOTELP = "notelp"
    private val KEY_LOGIN = "loginstate"
    private val KEY_CODE = "code"

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val editor = sharedPreferences.edit()

    fun setIdUser(user: User) {
        editor.putInt(KEY_USERID, user.idUser as Int)
        editor.apply()
    }

    fun setUser(user: User) {
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_PASSWORD, user.password)
        editor.putString(KEY_ADDRESS, user.address)
        editor.putString(KEY_NOTELP, user.noTelp)
        editor.apply()
    }

    fun setLoginState(login: Login) = editor.putBoolean(KEY_LOGIN, login.isValid).apply()

    fun setLoginCode(codeLogin: Int) = editor.putInt(KEY_CODE, codeLogin).apply()

    fun getIdUser(): User {
        val user = User()
        user.idUser = sharedPreferences.getInt(KEY_USERID, 0)
        return user
    }

    fun getLoginState(): Login {
        val login = Login()
        login.isValid = sharedPreferences.getBoolean(KEY_LOGIN, false)
        return login
    }

    fun getLoginCode(): Int = sharedPreferences.getInt(KEY_CODE, 0)

    fun getUser(): User {
        val user = User()
        user.username = sharedPreferences.getString(KEY_USERNAME, null)
        user.email = sharedPreferences.getString(KEY_EMAIL, null)
        user.password = sharedPreferences.getString(KEY_PASSWORD, null)
        user.address = sharedPreferences.getString(KEY_ADDRESS, null)
        user.noTelp = sharedPreferences.getString(KEY_NOTELP, null)
        return user
    }

    fun removeIdUser() {
        editor.remove(KEY_USERID)
        editor.apply()
    }

    fun removeLoginState() = editor.remove(KEY_LOGIN).apply()

    fun removeLoginCode() = editor.remove(KEY_CODE).apply()

    fun removeUser() {
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_PASSWORD)
        editor.remove(KEY_ADDRESS)
        editor.remove(KEY_NOTELP)
        editor.apply()
    }
}