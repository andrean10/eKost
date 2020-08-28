package com.example.eKost.model.datakost

import com.google.gson.annotations.SerializedName

data class ResponseKost(
	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
)
