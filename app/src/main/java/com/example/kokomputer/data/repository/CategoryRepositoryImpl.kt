package com.example.kokomputer.data.repository

import com.example.kokomputer.data.datasource.catalog.CategoryDataSource
import com.example.kokomputer.data.model.Category

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategoryData()
}