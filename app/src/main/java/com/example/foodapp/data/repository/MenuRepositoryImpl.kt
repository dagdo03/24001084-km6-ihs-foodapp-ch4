package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.menu.MenuDataSource
import com.example.foodapp.data.model.Menu

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenuData()
}