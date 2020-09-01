package com.example.eKost

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.session.UserPreferences

class StateViewModel : ViewModel() {
    private lateinit var dataState: MutableLiveData<Boolean>

    fun getState(context: Context): LiveData<Boolean> {
        dataState = MutableLiveData()

        val userPreferences = UserPreferences(context)
        val state = userPreferences.getLoginState().isValid
        dataState.postValue(state)
        return dataState
    }
}