package com.example.foodapp.data.mapper

import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.source.network.model.menu.MenuItemResponse

fun MenuItemResponse?.toMenu() =
    Menu(
        imgURL = this?.imageUrl.orEmpty(),
        name = this?.nama.orEmpty(),
        description = this?.detail.orEmpty(),
        location = this?.alamatResto.orEmpty(),
        locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
        price = this?.harga ?: 0,
    )

fun Collection<MenuItemResponse>?.toMenus() =
    this?.map {
        it.toMenu()
    } ?: listOf()
