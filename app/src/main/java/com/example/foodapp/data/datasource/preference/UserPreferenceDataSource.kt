package com.example.foodapp.data.datasource.preference

import android.content.SharedPreferences
import com.example.foodapp.utils.SharedPreferenceUtils.set

interface PreferenceDataSource {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class PreferenceDataSourceImpl(private val pref: SharedPreferences) : PreferenceDataSource {
    override fun isUsingGridMode(): Boolean = pref.getBoolean(KEY_IS_USING_GRID_MODE, false)

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        pref[KEY_IS_USING_GRID_MODE] = isUsingGridMode
    }

    companion object {
        const val PREF_NAME = "foodapp-pref"
        const val KEY_IS_USING_GRID_MODE = "KEY_IS_USING_GRID_MODE"
    }
}
