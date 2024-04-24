package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.preference.PreferenceDataSource

class PreferenceRepositoryImpl(private val dataSource: PreferenceDataSource) : PreferenceRepository {
    override fun isUsingGridMode(): Boolean {
        return dataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        dataSource.setUsingGridMode(isUsingGridMode)
    }
}