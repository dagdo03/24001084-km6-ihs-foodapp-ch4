package com.example.foodapp.data.repository

import com.example.foodapp.data.model.Menu

interface MenuRepository {
    fun getMenus() : List<Menu>
}