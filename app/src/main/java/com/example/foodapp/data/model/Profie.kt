package com.example.foodapp.data.model

import java.util.UUID

data class Profie(
    var id: String = UUID.randomUUID().toString(),
    val name: String,
    val username: String,
    val email: String,
    val profileImg: String,
)
