package com.example.kokomputer.data.datasource.user

import android.provider.ContactsContract
import com.example.kokomputer.data.model.Profie

class UserDataSourceImpl : UserDataSource {
    override fun isUsingDarkMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setUsingDarkMode(isUsingDarkMode: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getData(): List<Profie> {
        return listOf(
            Profie(
                name = "Ihsan Widagdo",
                username = "ihsan_03",
                email = "ihsan.widagdo@gmail.com",
                profileImg = "https://avatars.githubusercontent.com/u/95538168?s=96&v=4"
            ),
        )
    }
}