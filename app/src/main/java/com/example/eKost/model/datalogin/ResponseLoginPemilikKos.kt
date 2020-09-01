package com.example.eKost.model.datalogin

import com.google.gson.annotations.SerializedName

data class ResponseLoginPemilikKos(

	@field:SerializedName("data")
	val data: ResultsItemLoginPemilikKos? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
