package com.example.eKost.view.detail

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

class DetailViewModel: ViewModel() {

    companion object {
        private val TAG = DetailViewModel::class.simpleName
    }
    private lateinit var dataDetailKost: MutableLiveData<ArrayList<ResultsItem>>

    fun getDataKost(context: Context?, idKost: Int?): LiveData<ArrayList<ResultsItem>> {
        dataDetailKost = MutableLiveData()
        if (idKost != null) {
            detailKost(context, idKost)
        }
        return dataDetailKost
    }

    private fun detailKost(context: Context?, idKost: Int) {
        val client = ApiConfig.getApiService().getListIdKost(idKost)

        client.enqueue(object : Callback<ResponseKost> {
            override fun onResponse(call: Call<ResponseKost>, response: Response<ResponseKost>) {
                val result = response.body()?.results
                dataDetailKost.postValue(result as ArrayList<ResultsItem>?)
                Log.d(TAG, "onResponse: Data Berhasil Ditampilkan")
            }

            override fun onFailure(call: Call<ResponseKost>, t: Throwable) {
                dataDetailKost.postValue(null)
                Toast.makeText(context, "Data Gagal Ditampilkan", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: Data Gagal Ditampilkan karena ${t.message}")
            }
        })
    }

}