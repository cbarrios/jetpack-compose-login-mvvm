package com.lalosapps.mvvmlogin.login.data

import com.lalosapps.mvvmlogin.login.domain.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    override suspend fun loginUser(): Boolean {
        delay(3000)
        return (0..1).random() == 1
    }

    override suspend fun logoutUser(): Boolean {
        delay(1000)
        return true
    }
}