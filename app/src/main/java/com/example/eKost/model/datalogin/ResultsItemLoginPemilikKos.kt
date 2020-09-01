package com.example.eKost.model.datalogin

import com.google.gson.annotations.SerializedName

data class ResultsItemLoginPemilikKos(

    @field:SerializedName("id_hakakses")
    val idHakakses: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("id_pemilikkos")
    val idPemilikkos: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("tgl_daftar")
    val tglDaftar: String? = null,

    @field:SerializedName("no_telp")
    val noTelp: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
)