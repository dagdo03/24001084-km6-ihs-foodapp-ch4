package com.example.foodapp.data.repository

interface UserPreferenceRepository {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}
