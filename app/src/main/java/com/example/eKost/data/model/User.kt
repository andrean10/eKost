package com.example.eKost.data.model

data class User(
    var email: String? = null,
    var password: String? = null,
    var isValid: Boolean = false
)