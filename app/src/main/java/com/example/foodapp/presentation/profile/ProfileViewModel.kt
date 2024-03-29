package com.example.foodapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.model.Profie

class ProfileViewModel : ViewModel() {
    val profileData = MutableLiveData(
        Profie(
            name = "Ihsan Widagdo",
            username = "ihsan_03",
            email = "ihsan.widagdo@gmail.com",
            profileImg = "https://avatars.githubusercontent.com/u/95538168?s=96&v=4"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}