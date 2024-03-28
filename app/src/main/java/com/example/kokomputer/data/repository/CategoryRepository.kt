package com.example.kokomputer.data.repository

import com.example.kokomputer.data.model.Category

interface CategoryRepository {
    fun getCategories() : List<Category>
}