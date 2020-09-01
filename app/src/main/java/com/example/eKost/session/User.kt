package com.example.eKost.session

data class User(
    var idUser: Int? = 0,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var address: String? = null,
    var noTelp: String? = null
)