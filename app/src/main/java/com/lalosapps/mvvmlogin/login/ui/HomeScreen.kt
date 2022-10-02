package com.lalosapps.mvvmlogin.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    // We must call or reference this on the screen that has the logout event
    onLogout: () -> Unit
) {
    // In this screen we could have our NavHost to handle the app navigation
    val navController = rememberNavController()
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(it)
        ) {
            composable("home") {
                HomeScreenContent(
                    navigateToProfile = {
                        navController.navigate("profile")
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    onLogout = onLogout
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    navigateToProfile: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome!")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = navigateToProfile) {
                Text(text = "Go to Profile")
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Profile Screen")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onLogout) {
                Text(text = "Logout")
            }
        }
    }
}