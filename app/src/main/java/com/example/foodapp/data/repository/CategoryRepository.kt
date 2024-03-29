package com.example.foodapp.data.repository

import com.example.foodapp.data.model.Category

interface CategoryRepository {
    fun getCategories() : List<Category>
}