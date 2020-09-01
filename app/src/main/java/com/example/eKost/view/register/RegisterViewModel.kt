package com.example.eKost.view.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.model.dataregister.ResultRegister
import com.example.eKost.network.ApiConfig
import com.example.eKost.session.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    companion object {
        private val TAG = RegisterViewModel::class.simpleName
    }

    private lateinit var dataRegister: MutableLiveData<ResultRegister>

    fun getRegister(
        context: Context, username: String, email: String, password: String,
        address: String, numberTelephone: String,
    ): LiveData<ResultRegister> {
        dataRegister = MutableLiveData()

        // get codeRegister in data in preferences
        val userPreferences = UserPreferences(context)
        val codeRegister = userPreferences.getLoginCode()

        when (codeRegister) {
            2 -> {
                registerPencariKost(
                    context, username, email, password, address, numberTelephone,
                    codeRegister
                )
                Log.d(TAG, "getRegister: Pencari Kost")
            }
            3 -> {
                registerPemilikKost(
                    context, username, email, password, address, numberTelephone,
                    codeRegister
                )
                Log.d(TAG, "getRegister: Pemilik Kost")
            }
        }
        return dataRegister
    }

    private fun registerPencariKost(
        context: Context, username: String, email: String,
        password: String, addres: String, numberTelephone: String, codeRegister: Int
    ) {
        val client = ApiConfig.getApiService().getRegisterUserKost(
            username, email, password,
            addres, numberTelephone, codeRegister
        )

        client.enqueue(object : Callback<ResultRegister> {
            override fun onResponse(
                call: Call<ResultRegister>,
                result: Response<ResultRegister>
            ) {
                val data = result.body()
                Log.d(TAG, "onResponse: ${data?.message}")
                dataRegister.postValue(data)
            }

            override fun onFailure(call: Call<ResultRegister>, t: Throwable) {
                dataRegister.postValue(null)
                Toast.makeText(context, "Registrasi Gagal! Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun registerPemilikKost(
        context: Context, username: String, email: String, password: String,
        addres: String, numberTelephone: String, codeRegister: Int
    ) {
        val client = ApiConfig.getApiService().getRegisterPemilikKost(
            username, email, password,
            addres, numberTelephone, codeRegister
        )

        client.enqueue(object : Callback<ResultRegister> {
            override fun onResponse(
                call: Call<ResultRegister>,
                result: Response<ResultRegister>
            ) {
                val data = result.body()
                Log.d(TAG, "onResponse: ${data?.message}")
                dataRegister.postValue(data)
            }

            override fun onFailure(call: Call<ResultRegister>, t: Throwable) {
                dataRegister.postValue(null)
                Toast.makeText(context, "Registrasi Gagal! Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}