package com.lalosapps.mvvmlogin.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lalosapps.mvvmlogin.R
import com.lalosapps.mvvmlogin.main.ui.theme.*

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Login(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        HeaderImage(modifier = Modifier.align(CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        EmailField()
        Spacer(modifier = Modifier.height(4.dp))
        PasswordField()
        Spacer(modifier = Modifier.height(8.dp))
        ForgotPassword(modifier = Modifier.align(End))
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton()
    }
}

@Composable
fun LoginButton() {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = strongOrange,
            disabledBackgroundColor = softOrange,
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = stringResource(R.string.login_button))
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
fun PasswordField() {
    TextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.password_placeholder))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

@Preview("Email field", showBackground = true)
@Composable
fun EmailField() {
    TextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.email_placeholder))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
