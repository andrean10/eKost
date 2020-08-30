package com.example.eKost.view.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.model.dataregister.ResponseRegister
import com.example.eKost.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    companion object {
        private val TAG = RegisterViewModel::class.simpleName
    }

    private lateinit var dataRegister: MutableLiveData<ResponseRegister>

    fun getRegister(
        context: Context?, username: String, email: String, password: String,
        address: String, numberTelephone: String,
        codeRegister: Int
    ): LiveData<ResponseRegister> {
        dataRegister = MutableLiveData()

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
        context: Context?, username: String, email: String,
        password: String, addres: String, numberTelephone: String, codeRegister: Int
    ) {
        val client = ApiConfig.getApiService().getRegisterUserKost(
            username, email, password,
            addres, numberTelephone, codeRegister
        )

        client.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                val result = response.body()
                Log.d(TAG, "onResponse: ${result?.message}")
                dataRegister.postValue(result)
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                dataRegister.postValue(null)
                Toast.makeText(context, "Registrasi Gagal! Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun registerPemilikKost(
        context: Context?, username: String, email: String, password: String,
        addres: String, numberTelephone: String, codeRegister: Int
    ) {
        val client = ApiConfig.getApiService().getRegisterPemilikKost(
            username, email, password,
            addres, numberTelephone, codeRegister
        )

        client.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                val result = response.body()
                Log.d(TAG, "onResponse: ${result?.message}")
                dataRegister.postValue(result)
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                dataRegister.postValue(null)
                Toast.makeText(context, "Registrasi Gagal! Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}