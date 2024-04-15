package com.example.foodapp.data.datasource.category

import com.example.foodapp.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategoryData(): CategoriesResponse
}