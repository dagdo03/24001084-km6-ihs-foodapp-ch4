package com.example.foodapp.data.repository

interface PreferenceRepository {
    fun isUsingGridMode(): Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}