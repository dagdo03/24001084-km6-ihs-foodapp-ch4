package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.category.CategoryDataSource
import com.example.foodapp.data.model.Category

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategoryData()
}