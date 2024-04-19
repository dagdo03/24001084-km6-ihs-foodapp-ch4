package com.example.foodapp.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutItemPayload(
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("qty")
    val quantity: Int,
)
