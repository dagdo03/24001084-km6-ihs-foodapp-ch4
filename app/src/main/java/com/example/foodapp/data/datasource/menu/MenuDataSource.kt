package com.example.foodapp.data.datasource.menu

import com.example.foodapp.data.model.Menu

interface MenuDataSource {
    fun getMenuData(): List<Menu>
}