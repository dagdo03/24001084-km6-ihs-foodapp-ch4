package com.example.foodapp.data.datasource.category

import com.example.foodapp.data.source.network.model.category.CategoriesResponse
import com.example.foodapp.data.source.network.services.FoodAppApiService

class CategoryApiDataSource(private val service: FoodAppApiService) : CategoryDataSource {
    override suspend fun getCategoryData(): CategoriesResponse {
        return service.getCategories()
    }
}
