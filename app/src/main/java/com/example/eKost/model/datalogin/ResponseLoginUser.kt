package com.example.eKost.model.datalogin

import com.google.gson.annotations.SerializedName

data class ResponseLoginUser(

    @field:SerializedName("data")
    val data: ResultsItemLoginUser? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)


