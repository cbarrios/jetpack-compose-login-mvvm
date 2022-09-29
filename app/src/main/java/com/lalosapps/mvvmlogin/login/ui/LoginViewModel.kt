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

    var isSignedIn: Boolean? by mutableStateOf(null)
        private set

    var snack: String? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            // simulate asking our server about the user login status
            delay(1000)
            // let's say we are not logged in. Change this to test both behaviors
            isSignedIn = false
        }
    }

    fun logout() {
        email = ""
        password = ""
        loginEnabled = false
        loading = false
        snack = "Logout successful"
        isSignedIn = false
    }

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
            randomLogin()
        }
    }

    private fun randomLogin() {
        val ok = (0..1).random() == 1
        if (ok) {
            snack = "Login successful"
            isSignedIn = true
        } else {
            snack = "Couldn't login. Please try again."
            isSignedIn = false
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length > 6
    }
}