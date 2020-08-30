package com.example.eKost.network

import com.example.eKost.model.datakost.ResponseKost
import com.example.eKost.model.datalogin.ResponseLogin
import com.example.eKost.model.dataregister.ResponseRegister
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("index.php")
    fun getListKost(): Call<ResponseKost>

    @GET("idkost.php")
    fun getListIdKost(
        @Query("idkos") idkost: Int
    ): Call<ResponseKost>

    @FormUrlEncoded
    @POST("checklogin.php")
    fun getListUserKost(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("code") code: Int
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("checklogin.php")
    fun getListPemilikKost(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("code") code: Int
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("register.php")
    fun getRegisterUserKost(
        @Field("nama") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") address: String,
        @Field("no_telp") numberTelephone: String,
        @Field("code") code: Int
    ): Call<ResponseRegister>

    @FormUrlEncoded
    @POST("register.php")
    fun getRegisterPemilikKost(
        @Field("nama") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") address: String,
        @Field("no_telp") numberTelephone: String,
        @Field("code") code: Int
    ): Call<ResponseRegister>
}