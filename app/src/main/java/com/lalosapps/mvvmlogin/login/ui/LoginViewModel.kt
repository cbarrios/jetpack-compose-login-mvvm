package com.lalosapps.mvvmlogin.login.ui

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalosapps.mvvmlogin.login.core.SnackMessage
import com.lalosapps.mvvmlogin.login.data.AuthRepositoryImpl
import com.lalosapps.mvvmlogin.login.domain.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

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

    var snack: SnackMessage by mutableStateOf(SnackMessage())
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
        viewModelScope.launch {
            isSignedIn = null
            val signedOut = authRepository.logoutUser()
            if (signedOut) {
                email = ""
                password = ""
                loginEnabled = false
                loading = false
                snack = SnackMessage(message = "Logout successful")
                isSignedIn = false
            } else {
                // never going to happen in our case but it could happen on a server error
                isSignedIn = true
            }
        }
    }

    fun onSnackCompleted() {
        snack = snack.copy(message = null)
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
            val loggedIn = authRepository.loginUser()
            loginEnabled = isValidEmail(email) && isValidPassword(password)
            loading = false
            randomLogin(loggedIn)
        }
    }

    private fun randomLogin(loggedIn: Boolean) {
        if (loggedIn) {
            snack = snack.copy(message = "Login successful")
            isSignedIn = true
        } else {
            snack = snack.copy(id = snack.id.plus(1), message = "Couldn't login. Please try again.")
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