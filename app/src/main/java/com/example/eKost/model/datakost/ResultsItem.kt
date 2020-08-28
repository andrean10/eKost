package com.example.eKost.model.datakost

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsItem(

    @field:SerializedName("idkos")
    val idkos: String? = null,

    @field:SerializedName("id_pemilikkos")
    val idPemilikkos: String? = null,

    @field:SerializedName("lng")
    val lng: String? = null,

    @field:SerializedName("namakos")
    val namakos: String? = null,

    @field:SerializedName("kategori")
    val kategori: String? = null,

    @field:SerializedName("gambar_a")
    val gambarA: String? = null,

    @field:SerializedName("gambar_b")
    val gambarB: String? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null,

    @field:SerializedName("aktif")
    val aktif: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("harga")
    val harga: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("no_telp")
    val noTelp: String? = null,

    @field:SerializedName("per")
    val per: String? = null,

    @field:SerializedName("jumlah_kos")
    val jumlahKos: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null
): Parcelable