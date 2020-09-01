package com.example.eKost.view.profile.user

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.model.dataProfile.ResponseProfile
import com.example.eKost.network.ApiConfig
import com.example.eKost.session.User
import com.example.eKost.session.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private lateinit var dataProfile: MutableLiveData<ResponseProfile>
    private val TAG = ProfileViewModel::class.java.simpleName

    fun getProfile(context: Context): LiveData<ResponseProfile> {
        dataProfile = MutableLiveData()

        val userPreferences = UserPreferences(context)
        val dataIdUser = userPreferences.getIdUser().idUser.toString()

        profileUser(context, dataIdUser)
        return dataProfile
    }

    private fun profileUser(context: Context, idUser: String) {
        val client = ApiConfig.getApiService().getProfileUser(idUser)

        client.enqueue(object : Callback<ResponseProfile> {
            override fun onResponse(
                call: Call<ResponseProfile>, response: Response<ResponseProfile>
            ) {
                val result = response.body()

                val userPreferences = UserPreferences(context)

                // kirim data jika kode 1
                if (result?.kode == 1) {
                    userPreferences.setUser(
                        User(
                            result.data?.idUser?.toInt(),
                            result.data?.nama,
                            result.data?.email,
                            result.data?.password,
                            result.data?.alamat,
                            result.data?.noTelp
                        )
                    )
                    dataProfile.postValue(result)
                } else { // gagal mengambil data
                    dataProfile.postValue(result)
                }
            }

            override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
                dataProfile.postValue(null)
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
}