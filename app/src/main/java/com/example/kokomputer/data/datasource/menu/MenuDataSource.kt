package com.example.kokomputer.data.datasource.menu

import com.example.kokomputer.data.model.Menu

interface MenuDataSource {
    fun getMenuData(): List<Menu>
}