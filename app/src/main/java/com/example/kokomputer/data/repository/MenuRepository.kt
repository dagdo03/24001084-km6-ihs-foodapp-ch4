package com.example.kokomputer.data.repository

import com.example.kokomputer.data.model.Menu

interface MenuRepository {
    fun getMenus() : List<Menu>
}