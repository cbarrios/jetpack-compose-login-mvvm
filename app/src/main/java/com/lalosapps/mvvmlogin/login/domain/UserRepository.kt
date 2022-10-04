package com.lalosapps.mvvmlogin.login.domain

import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    fun getUser(): StateFlow<User>

    fun setUser(user: User)

    fun updateUserEmail(email: String)

    fun updateUserData(data: List<String>)

    suspend fun getUserData(): List<String>
}

data class User(
    val email: String = "",
    val data: List<String> = emptyList()
)