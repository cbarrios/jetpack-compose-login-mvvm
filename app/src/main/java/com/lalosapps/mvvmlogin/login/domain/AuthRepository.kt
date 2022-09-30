package com.lalosapps.mvvmlogin.login.domain

interface AuthRepository {

    suspend fun loginUser(): Boolean

    suspend fun logoutUser(): Boolean
}