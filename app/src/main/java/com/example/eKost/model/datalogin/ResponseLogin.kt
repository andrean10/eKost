package com.example.eKost.model.datalogin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseLogin(
	val data: ResultsItemLogin? = null,
	val message: String? = null,
	val status: Int? = null
) : Parcelable


