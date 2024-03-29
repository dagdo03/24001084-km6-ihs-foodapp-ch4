package com.example.foodapp.presentation.home

import androidx.lifecycle.ViewModel
import com.example.foodapp.data.repository.CategoryRepository
import com.example.foodapp.data.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    fun getCategories() = categoryRepository.getCategories()
    fun getMenus() = menuRepository.getMenus()
}