package com.lalosapps.mvvmlogin.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    // We must call or reference this on the screen that has the logout event
    onLogout: () -> Unit
) {
    // In this screen we could have our NavHost to handle the app navigation
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
            Button(onClick = onLogout) {
                Text(text = "Logout")
            }
        }
    }
}