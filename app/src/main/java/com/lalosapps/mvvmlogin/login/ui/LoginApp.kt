package com.lalosapps.mvvmlogin.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lalosapps.mvvmlogin.main.ui.theme.MVVMLoginTheme

@Composable
fun LoginApp() {
    // Single Source of Truth for login and logout
    val viewModel: LoginViewModel = viewModel()
    val isSignedIn = viewModel.isSignedIn
    val user = viewModel.user.collectAsState().value

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(viewModel.snack) {
        val message = viewModel.snack.message
        if (message != null) {
            scaffoldState.snackbarHostState.showSnackbar(message)
            viewModel.onSnackCompleted()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        AnimatedVisibility(visible = isSignedIn == null, enter = fadeIn(), exit = fadeOut()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(visible = isSignedIn == true, enter = fadeIn(), exit = fadeOut()) {
            MVVMLoginTheme {
                HomeScreen(
                    user = user,
                    updateUserData = viewModel::updateUserData,
                    onLogout = viewModel::logout
                )
            }
        }

        AnimatedVisibility(visible = isSignedIn == false, enter = fadeIn(), exit = fadeOut()) {
            LoginScreen(
                email = viewModel.email,
                password = viewModel.password,
                loginEnabled = viewModel.loginEnabled,
                loading = viewModel.loading,
                onEmailChanged = { viewModel.onLoginChanged(it, viewModel.password) },
                onPasswordChanged = { viewModel.onLoginChanged(viewModel.email, it) },
                onLoginClick = viewModel::onLoginSelected
            )
        }
    }
}