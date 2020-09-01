package com.example.eKost.model.editprofile

import com.google.gson.annotations.SerializedName

data class ResponseEditProfile(

	@field:SerializedName("data")
	val data: ResultEditProfile? = null,

	@field:SerializedName("kode")
	val kode: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
