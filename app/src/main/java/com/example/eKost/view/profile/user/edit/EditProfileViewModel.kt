package com.example.eKost.view.profile.user.edit

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.model.editprofile.ResponseEditProfile
import com.example.eKost.network.ApiConfig
import com.example.eKost.session.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel : ViewModel() {
    private lateinit var dataEditProfile: MutableLiveData<ResponseEditProfile>

    private val TAG = EditProfileViewModel::class.java.simpleName

    fun getEditProfile(
        context: Context, username: String, email: String, password: String, address: String,
        noTelephone: String
    ): LiveData<ResponseEditProfile> {
        dataEditProfile = MutableLiveData()

        // ambil data iduser
        val userPreferences = UserPreferences(context)
        val idUser = userPreferences.getIdUser().idUser as Int

        editProfile(context, username, email, password, address, noTelephone, idUser)
        return dataEditProfile
    }

    private fun editProfile(
        context: Context, username: String, email: String, password: String, address: String,
        noTelephone: String, idUser: Int
    ) {
        val client = ApiConfig.getApiService()
            .getEditUserKost(username, email, password, address, noTelephone, idUser)

        client.enqueue(object : Callback<ResponseEditProfile> {
            override fun onResponse(
                call: Call<ResponseEditProfile>,
                response: Response<ResponseEditProfile>
            ) {
                val result = response.body()

                if (result?.kode == 1) {
                    dataEditProfile.postValue(result)
                } else {
                    dataEditProfile.postValue(result)
                }

                Log.d(TAG, "onResponse: ${result?.data}")
            }

            override fun onFailure(call: Call<ResponseEditProfile>, t: Throwable) {
                dataEditProfile.postValue(null)
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}