package com.example.kokomputer.data.model

import java.util.UUID

data class Category(
    var id: String = UUID.randomUUID().toString(),
    var imgURL: String,
    var name: String,
)
