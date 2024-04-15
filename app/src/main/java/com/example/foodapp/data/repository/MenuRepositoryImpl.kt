package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.menu.MenuDataSource
import com.example.foodapp.data.mapper.toMenus
import com.example.foodapp.data.model.Menu
import com.example.foodapp.utils.ResultWrapper
import com.example.foodapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenuData(categorySlug).data.toMenus()
        }
    }

}