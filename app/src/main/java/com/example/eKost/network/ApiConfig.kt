package com.example.eKost.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private val URL_EKOST = "http://192.168.43.42/eKost/api/"

        fun getApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(URL_EKOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}