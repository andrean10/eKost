package com.example.eKost.model.datalogin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsItemLogin(
    val idHakakses: String? = null,
    val password: String? = null,
    val nama: String? = null,
    val idUser: String? = null,
    val noTelp: String? = null,
    val email: String? = null,
    val alamat: String? = null
) : Parcelable