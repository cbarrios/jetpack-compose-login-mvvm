package com.lalosapps.mvvmlogin.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lalosapps.mvvmlogin.R
import com.lalosapps.mvvmlogin.main.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val email = viewModel.email
    val password = viewModel.password
    val loginEnabled = viewModel.loginEnabled
    val loading = viewModel.loading

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.snacks.collectLatest {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Login(
                email = email,
                password = password,
                loginEnabled = loginEnabled,
                loading = loading,
                onEmailChanged = {
                    viewModel.onLoginChanged(it, password)
                },
                onPasswordChanged = {
                    viewModel.onLoginChanged(email, it)
                },
                onLoginSelected = viewModel::onLoginSelected,
                modifier = Modifier.align(Center)
            )
        }
    }

}

@Composable
fun Login(
    email: String,
    password: String,
    loginEnabled: Boolean,
    loading: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginSelected: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        HeaderImage(modifier = Modifier.align(CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        EmailField(
            email = email,
            onEmailChanged = onEmailChanged
        )
        Spacer(modifier = Modifier.height(4.dp))
        PasswordField(
            password = password,
            onPasswordChanged = onPasswordChanged
        )
        Spacer(modifier = Modifier.height(8.dp))
        ForgotPassword(modifier = Modifier.align(End))
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(
            loginEnabled = loginEnabled,
            loading = loading,
            onLoginSelected = onLoginSelected
        )
    }
}

@Composable
fun LoginButton(
    loginEnabled: Boolean,
    loading: Boolean,
    onLoginSelected: () -> Unit
) {
    Button(
        onClick = onLoginSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = strongOrange,
            disabledBackgroundColor = softOrange,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = loginEnabled
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                color = Color.White
            )
        } else {
            Text(text = stringResource(R.string.login_button))
        }
    }
}

@Composable
fun ForgotPassword(modifier: Modifier = Modifier) {
    Text(
        text = "Forgot password?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = orange
    )
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = password,
        onValueChange = onPasswordChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.password_placeholder))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = grey,
            backgroundColor = lightGrey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun EmailField(
    email: String,
    onEmailChanged: (String) -> Unit
) {
    TextField(
        value = email,
        onValueChange = onEmailChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.email_placeholder))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = grey,
            backgroundColor = lightGrey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun HeaderImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.aris),
        contentDescription = stringResource(R.string.header),
        modifier = modifier
    )
}
