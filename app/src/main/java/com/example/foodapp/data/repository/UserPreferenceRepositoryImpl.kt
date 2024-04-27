package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.preference.PreferenceDataSource

class UserPreferenceRepositoryImpl(private val dataSource: PreferenceDataSource) : UserPreferenceRepository {
    override fun isUsingGridMode(): Boolean {
        return dataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        dataSource.setUsingGridMode(isUsingGridMode)
    }
}
