package com.example.foodapp.presentation.main

import androidx.lifecycle.ViewModel
import com.example.foodapp.data.repository.UserRepository

class MainViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun isLoggedIn() =
        userRepository
            .isLoggedIn()
}
