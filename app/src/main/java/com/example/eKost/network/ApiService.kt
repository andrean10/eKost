package com.example.eKost.network

import com.example.eKost.model.datakost.ResponseKost
import com.example.eKost.model.datalogin.ResponseLogin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("index.php")
    fun getListKost(): Call<ResponseKost>

    @GET("idkost.php")
    fun getListIdKost(
        @Query("idkos") idkost: Int
    ): Call<ResponseKost>

    @GET("checklogin.php")
    fun getListUserKost(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("code") code: Int
    ): Call<ResponseLogin>

    @GET("checklogin.php")
    fun getListPemilikKost(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("code") code: Int
    ): Call<ResponseLogin>
}