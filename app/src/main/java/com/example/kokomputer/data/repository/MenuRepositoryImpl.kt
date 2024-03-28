package com.example.kokomputer.data.repository

import com.example.kokomputer.data.datasource.menu.MenuDataSource
import com.example.kokomputer.data.model.Menu

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenuData()
}