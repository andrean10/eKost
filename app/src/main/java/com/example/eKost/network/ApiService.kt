package com.example.eKost.network

import com.example.eKost.model.dataProfile.ResponseProfile
import com.example.eKost.model.datakost.ResponseKost
import com.example.eKost.model.datalogin.ResponseLoginPemilikKos
import com.example.eKost.model.datalogin.ResponseLoginUser
import com.example.eKost.model.dataregister.ResultRegister
import com.example.eKost.model.editprofile.ResponseEditProfile
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
    ): Call<ResponseLoginUser>

    @FormUrlEncoded
    @POST("checklogin.php")
    fun getListPemilikKost(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("code") code: Int
    ): Call<ResponseLoginPemilikKos>

    @FormUrlEncoded
    @POST("register.php")
    fun getRegisterUserKost(
        @Field("nama") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") address: String,
        @Field("no_telp") numberTelephone: String,
        @Field("code") code: Int
    ): Call<ResultRegister>

    @FormUrlEncoded
    @POST("register.php")
    fun getRegisterPemilikKost(
        @Field("nama") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") address: String,
        @Field("no_telp") numberTelephone: String,
        @Field("code") code: Int
    ): Call<ResultRegister>

    @FormUrlEncoded
    @POST("profileuser.php")
    fun getProfileUser(
        @Field("id_user") idUser: String
    ): Call<ResponseProfile>

    @FormUrlEncoded
    @POST("profilepemilikkos.php")
    fun getProfilePemilikKos(
        @Field("id_pemilikkos") idPemilikKos: String
    ): Call<ResponseProfile>

    @FormUrlEncoded
    @POST("editprofile.php")
    fun getEditUserKost(
        @Field("nama") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") address: String,
        @Field("no_telp") numberTelephone: String,
        @Field("id_user") idUser: Int,
    ): Call<ResponseEditProfile>
}