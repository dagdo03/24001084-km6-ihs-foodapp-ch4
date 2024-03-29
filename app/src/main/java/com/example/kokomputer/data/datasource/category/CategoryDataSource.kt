package com.example.kokomputer.data.datasource.category

import com.example.kokomputer.data.model.Category

interface CategoryDataSource {
    fun getCategoryData(): List<Category>
}