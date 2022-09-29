package com.lalosapps.mvvmlogin.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginApp() {
    // Single Source of Truth for login and logout
    val viewModel: LoginViewModel = viewModel()
    val isSignedIn = viewModel.isSignedIn

    val scaffoldState = rememberScaffoldState()
    viewModel.snack?.let { snack ->
        LaunchedEffect(snack) {
            scaffoldState.snackbarHostState.showSnackbar(snack)
            viewModel.onSnackCompleted()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        if (isSignedIn == null) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (isSignedIn) {
                HomeScreen(
                    onLogout = viewModel::logout
                )
            } else {
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
}