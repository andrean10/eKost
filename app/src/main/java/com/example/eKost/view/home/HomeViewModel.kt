package com.example.eKost.view.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eKost.model.datakost.ResponseKost
import com.example.eKost.model.datakost.ResultsItem
import com.example.eKost.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.simpleName
    }
    private lateinit var dataListKost: MutableLiveData<ArrayList<ResultsItem>>

    fun getKost(context: Context?) : LiveData<ArrayList<ResultsItem>> {
        dataListKost = MutableLiveData()
        kostList(context)
        return dataListKost
    }

    private fun kostList(context: Context?) {
        val client = ApiConfig.getApiService().getListKost()

        client.enqueue(object : Callback<ResponseKost> {
            override fun onResponse(call: Call<ResponseKost>, response: Response<ResponseKost>) {
                val result = response.body()?.results
                dataListKost.postValue(result as ArrayList<ResultsItem>?)
                Log.d(TAG, "onResponse: Data Berhasil Ditampilkan")
            }

            override fun onFailure(call: Call<ResponseKost>, t: Throwable) {
                dataListKost.postValue(null)
                Toast.makeText(context, "Data Gagal Ditampilkan", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: Data Gagal Ditampilkan karena ${t.message}")
            }
        })
    }
}