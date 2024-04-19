package com.example.foodapp.data.datasource.menu

import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodapp.data.source.network.model.checkout.CheckoutResponse
import com.example.foodapp.data.source.network.model.menu.MenusResponse

interface MenuDataSource {
    suspend fun getMenuData(categorySlug: String? = null): MenusResponse
    suspend fun createOrder(payload : CheckoutRequestPayload) : CheckoutResponse
}