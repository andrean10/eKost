package com.example.eKost.model.dataProfile

import com.google.gson.annotations.SerializedName

data class ResponseProfile(

	@field:SerializedName("data")
	val data: ResultProfile? = null,

	@field:SerializedName("kode")
	val kode: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)