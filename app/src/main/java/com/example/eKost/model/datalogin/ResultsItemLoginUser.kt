package com.example.eKost.model.datalogin

import com.google.gson.annotations.SerializedName

data class ResultsItemLoginUser(

    @field:SerializedName("id_hakakses")
    val idHakakses: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("no_telp")
    val noTelp: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
)