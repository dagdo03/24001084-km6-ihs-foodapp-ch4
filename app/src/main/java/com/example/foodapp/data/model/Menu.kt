package com.example.foodapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String = UUID.randomUUID().toString(),
    var imgURL: String,
    var name: String,
    var description: String,
    var location: String,
    var locationURL: String,
    var price: Int,
) : Parcelable
