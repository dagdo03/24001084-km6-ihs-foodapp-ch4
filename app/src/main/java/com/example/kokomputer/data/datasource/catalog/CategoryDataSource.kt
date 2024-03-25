package com.example.kokomputer.data.datasource.catalog

import com.example.kokomputer.data.model.Category

interface CategoryDataSource {
    fun getCategoryData(): List<Category>
}