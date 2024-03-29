package com.example.foodapp.data.source.local.preference

interface UserPreference {
    fun isUsingListMode(): Boolean
    fun setUsingGridMode(isUsingDarkMode: Boolean)
}