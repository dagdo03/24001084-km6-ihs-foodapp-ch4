package com.example.kokomputer.data.model

import java.util.UUID

data class Menu(
    var id: String = UUID.randomUUID().toString(),
    var imgURL: String,
    var name: String,
    var description: String,
    var location: String,
    var locationURL: String,
    var price: Double
)
