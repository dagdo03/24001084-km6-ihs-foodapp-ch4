package com.example.foodapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun doLogin(email: String, password: String) =
        repository
            .doLogin(
                email = email,
                password = password)
            .asLiveData(Dispatchers.IO)
}