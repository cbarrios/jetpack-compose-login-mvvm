package com.lalosapps.mvvmlogin.login.ui

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
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

    var snack: String? by mutableStateOf(null)
        private set

    fun onSnackCompleted() {
        snack = null
    }

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
            snack = "Login successful"
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length > 6
    }
}