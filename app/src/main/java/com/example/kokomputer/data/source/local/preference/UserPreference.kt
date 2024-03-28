package com.example.kokomputer.data.source.local.preference

interface UserPreference {
    fun isUsingListMode(): Boolean
    fun setUsingGridMode(isUsingDarkMode: Boolean)
}