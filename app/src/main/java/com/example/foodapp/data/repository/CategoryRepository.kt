package com.example.foodapp.data.repository

import com.example.foodapp.data.model.Category
import com.example.foodapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories() : Flow<ResultWrapper<List<Category>>>
}