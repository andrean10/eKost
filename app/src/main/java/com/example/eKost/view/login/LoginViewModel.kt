package com.example.eKost.view.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.model.datalogin.ResponseLogin
import com.example.eKost.model.datalogin.ResultsItemLogin
import com.example.eKost.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    companion object {
        private val TAG = LoginViewModel::class.simpleName
    }
    private lateinit var dataLogin: MutableLiveData<ResultsItemLogin>

    fun getLogin(context: Context?, email: String, password: String, codeLogin: Int) : LiveData<ResultsItemLogin> {
        dataLogin = MutableLiveData()

        when(codeLogin) {
            2 -> {
                dataLoginUser(context, email, password, codeLogin)
                Log.d(TAG, "getLogin: Pencari Kost")
            }
            3 -> {
                dataLoginPemilikKost(context, email, password, codeLogin)
                Log.d(TAG, "getLogin: Pemilik Kost")
            }
        }
        return dataLogin
    }

    private fun dataLoginUser(context: Context?, email: String, password: String, codeLogin: Int) {
        val client = ApiConfig.getApiService().getListUserKost(email, password, codeLogin)

        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val result = response.body()
                val status = result?.status
                val message = result?.message
                val data = result?.data

                when (status) {
                    2 -> {
                        dataLogin.postValue(data)
                        Log.d(TAG, "onResponse: $message")
                    }
                    1 -> {
                        dataLogin.postValue(data)
                        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onResponse: $message")
                    }
                    0 -> {
                        dataLogin.postValue(data)
                        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onResponse: $message")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                dataLogin.postValue(null)
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun dataLoginPemilikKost(
        context: Context?,
        email: String,
        password: String,
        codeLogin: Int
    ) {
        val client = ApiConfig.getApiService().getListPemilikKost(email, password, codeLogin)

        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val result = response.body()
                val status = result?.status
                val message = result?.message
                val data = result?.data

                when (status) {
                    2 -> {
                        dataLogin.postValue(data)
                        Log.d(TAG, "onResponse: $message")
                    }
                    1 -> {
                        dataLogin.postValue(data)
                        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onResponse: $message")
                    }
                    0 -> {
                        dataLogin.postValue(data)
                        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onResponse: $message")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                dataLogin.postValue(null)
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

}