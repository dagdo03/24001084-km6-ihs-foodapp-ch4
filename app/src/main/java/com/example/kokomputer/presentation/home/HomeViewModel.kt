package com.example.kokomputer.presentation.home

import androidx.lifecycle.ViewModel
import com.example.kokomputer.data.repository.CategoryRepository
import com.example.kokomputer.data.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    fun getCategories() = categoryRepository.getCategories()
    fun getMenus() = menuRepository.getMenus()
}