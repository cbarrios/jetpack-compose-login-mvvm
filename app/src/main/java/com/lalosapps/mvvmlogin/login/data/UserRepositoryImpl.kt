package com.lalosapps.mvvmlogin.login.data

import com.lalosapps.mvvmlogin.login.domain.User
import com.lalosapps.mvvmlogin.login.domain.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class UserRepositoryImpl : UserRepository {

    private val _user = MutableStateFlow(User())
    private val user = _user.asStateFlow()

    override fun getUser(): StateFlow<User> = user

    override fun setUser(user: User) {
        _user.value = user
    }

    override fun updateUserEmail(email: String) {
        _user.update { it.copy(email = email) }
    }

    override fun updateUserData(data: List<String>) {
        _user.update { it.copy(data = data) }
    }

    override suspend fun getUserData(): List<String> {
        return withContext(Dispatchers.IO) {
            delay(2000)
            listOf("God", "Family", "Android")
        }
    }
}