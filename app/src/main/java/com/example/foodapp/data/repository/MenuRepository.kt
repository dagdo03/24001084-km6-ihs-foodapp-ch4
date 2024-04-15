package com.example.foodapp.data.repository

import com.example.foodapp.data.model.Menu
import com.example.foodapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(categorySlug : String? = null) : Flow<ResultWrapper<List<Menu>>>
}