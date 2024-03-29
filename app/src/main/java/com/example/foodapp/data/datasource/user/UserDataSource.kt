package com.example.foodapp.data.datasource.user

import com.example.foodapp.data.model.Profie

interface UserDataSource {
    fun isUsingDarkMode(): Boolean
    fun setUsingDarkMode(isUsingDarkMode: Boolean)
    fun getData() : List<Profie>
}