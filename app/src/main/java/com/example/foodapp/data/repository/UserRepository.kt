package com.example.foodapp.data.repository

import com.example.foodapp.data.model.User
import com.example.foodapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    @Throws(exceptionClasses = [Exception::class])
    fun doLogin(
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>>

    @Throws(exceptionClasses = [Exception::class])
    fun doRegister(
        email: String,
        fullName: String,
        password: String
    ): Flow<ResultWrapper<Boolean>>

    fun updateProfile(fullName: String? = null): Flow<ResultWrapper<Boolean>>
    fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>>
    fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>>
    fun requestChangePasswordByEmail(): Boolean
    fun doLogout(): Boolean
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
}