package com.example.foodapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodapp.data.repository.CategoryRepository
import com.example.foodapp.data.repository.MenuRepository
import com.example.foodapp.data.repository.UserPreferenceRepository
import com.example.foodapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPreference: UserPreferenceRepository,
    private val authRepository: UserRepository,
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(userPreference.isUsingGridMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun getListMode(): Int {
        return if (userPreference.isUsingGridMode()) 1 else 0
    }

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        userPreference.setUsingGridMode(!currentValue)
    }

    fun getMenu(categoryName: String? = null) = menuRepository.getMenus(categoryName).asLiveData(Dispatchers.IO)

    fun getCategory() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun getCurrentUser() = authRepository.getCurrentUser()

    fun isLoggedIn() = authRepository.isLoggedIn()
}
