package com.example.kokomputer.data.datasource.user

import android.provider.ContactsContract
import com.example.kokomputer.data.model.Profie

interface UserDataSource {
    fun isUsingDarkMode(): Boolean
    fun setUsingDarkMode(isUsingDarkMode: Boolean)
    fun getData() : List<Profie>
}