package com.example.foodapp.data.source.local.preference

class UserPreferenceImpl : UserPreference {
    override fun isUsingListMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setUsingGridMode(isUsingDarkMode: Boolean) {
        TODO("Not yet implemented")
    }
    companion object {
        const val PREF_NAME = "kokomputer-pref"
        const val KEY_IS_USING_DARK_MODE = "KEY_IS_USING_LIST_MODE"
    }
}