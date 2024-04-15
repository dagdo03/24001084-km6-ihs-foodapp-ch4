package com.example.foodapp.data.datasource.menu

import com.example.foodapp.data.source.network.model.menu.MenusResponse
import com.example.foodapp.data.source.network.services.FoodAppApiService

class MenuApiDataSource(private val service : FoodAppApiService): MenuDataSource {
    override suspend fun getMenuData(categorySlug: String?): MenusResponse {
        return service.getMenus(categorySlug)
    }

}