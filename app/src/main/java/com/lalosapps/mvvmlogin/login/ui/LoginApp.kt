package com.lalosapps.mvvmlogin.login.ui

import androidx.compose.animation.Crossfade
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lalosapps.mvvmlogin.main.ui.theme.MVVMLoginTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginApp() {
    // Single Source of Truth for login and logout
    val viewModel: LoginViewModel = viewModel()
    val isSignedIn = viewModel.isSignedIn
    val user = viewModel.user.collectAsStateWithLifecycle().value

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
        Crossfade(targetState = isSignedIn) {
            when (it) {
                null -> Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                true -> MVVMLoginTheme {
                    HomeScreen(
                        user = user,
                        updateUserData = viewModel::updateUserData,
                        onLogout = viewModel::logout
                    )
                }
                false -> LoginScreen(
                    email = viewModel.email,
                    password = viewModel.password,
                    loginEnabled = viewModel.loginEnabled,
                    loading = viewModel.loading,
                    onEmailChanged = { email ->
                        viewModel.onLoginChanged(
                            email,
                            viewModel.password
                        )
                    },
                    onPasswordChanged = { pass -> viewModel.onLoginChanged(viewModel.email, pass) },
                    onLoginClick = viewModel::onLoginSelected
                )
            }
        }
    }
}