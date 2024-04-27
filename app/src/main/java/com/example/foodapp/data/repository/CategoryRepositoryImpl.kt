package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.category.CategoryDataSource
import com.example.foodapp.data.mapper.toCategories
import com.example.foodapp.data.model.Category
import com.example.foodapp.utils.ResultWrapper
import com.example.foodapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategoryData().data.toCategories() }
    }
}
