package com.example.kokomputer.data.repository

import com.example.kokomputer.data.datasource.category.CategoryDataSource
import com.example.kokomputer.data.model.Category

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategoryData()
}