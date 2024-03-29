package com.example.foodapp.data.datasource.category

import com.example.foodapp.data.model.Category

interface CategoryDataSource {
    fun getCategoryData(): List<Category>
}