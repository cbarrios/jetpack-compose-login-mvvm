package com.lalosapps.mvvmlogin.login.ui

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginEnabled by mutableStateOf(false)
        private set

    var loading by mutableStateOf(false)
        private set

    private val _snacks = Channel<String>()
    val snacks = _snacks.receiveAsFlow()

    fun onLoginChanged(email: String, password: String) {
        this.email = email
        this.password = password
        if (!loading) loginEnabled = isValidEmail(email) && isValidPassword(password)
    }

    fun onLoginSelected() {
        viewModelScope.launch {
            loading = true
            loginEnabled = false
            delay(3000)
            loginEnabled = isValidEmail(email) && isValidPassword(password)
            loading = false
            _snacks.send("Login successful")
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length > 6
    }
}