package com.example.foodapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodapp.data.model.Profie
import com.example.foodapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val profileData = MutableLiveData(
        Profie(
            name = "Ihsan Widagdo",
            username = "ihsan_03",
            email = "ihsan.widagdo@gmail.com",
            profileImg = "https://avatars.githubusercontent.com/u/95538168?s=96&v=4"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun doLogout() =
        userRepository
            .doLogout()


    fun updateProfileName(fullName: String) =
        userRepository
            .updateProfile(fullName = fullName)
            .asLiveData(Dispatchers.IO)

    fun updateProfileEmail(email : String) =
        userRepository
            .updateEmail(newEmail = email)
            .asLiveData(Dispatchers.IO)

    fun getCurrentUser() =
        userRepository
            .getCurrentUser()

    fun createChangePwdRequest() {
        userRepository.requestChangePasswordByEmail()
    }

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}