package com.appmoviles.gymrat20.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.ui.home.AdminOneActivity
import com.appmoviles.gymrat20.ui.home.HomeActivity
import com.appmoviles.gymrat20.ui.auth.SignUpActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.auth.LoginNav
import com.appmoviles.gymrat20.viewmodel.auth.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymRat20Theme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen(vm: LoginViewModel = viewModel()) {
    val context = LocalContext.current

    val email by vm.email
    val password by vm.password
    val errorMsg by vm.errorMsg
    val nav by vm.navigateTo

    // ✅ Navegación controlada desde el VM (evento)
    LaunchedEffect(nav) {
        when (nav) {
            LoginNav.Home -> {
                context.startActivity(Intent(context, HomeActivity::class.java))
                vm.consumeNavigation()
            }

            LoginNav.Admin -> {
                context.startActivity(Intent(context, AdminOneActivity::class.java))
                vm.consumeNavigation()
            }

            LoginNav.SignUp -> {
                context.startActivity(Intent(context, SignUpActivity::class.java))
                vm.consumeNavigation()
            }

            null -> Unit
        }
    }

    LoginContent(
        email = email,
        password = password,
        errorMsg = errorMsg,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onLoginClick = vm::login,
        onForgotClick = vm::forgotPassword,
        onSignUpClick = vm::goToSignUp
    )
}

@Composable
fun LoginContent(
    email: String,
    password: String,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color(0xFF1AA39A)),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.Companion.height(30.dp))

            AppTitle()

            Spacer(modifier = Modifier.Companion.height(24.dp))

            LoginCard(
                email = email,
                password = password,
                errorMsg = errorMsg,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClick = onLoginClick,
                onForgotClick = onForgotClick,
                onSignUpClick = onSignUpClick
            )

            Spacer(modifier = Modifier.Companion.height(30.dp))
        }
    }
}

@Composable
fun AppTitle() {
    Text(
        text = "App Name",
        fontSize = 34.sp,
        fontWeight = FontWeight.Companion.Bold,
        color = Color.Companion.White
    )
}

@Composable
fun LoginCard(
    email: String,
    password: String,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
        modifier = Modifier.Companion.fillMaxWidth()
    ) {
        Column(modifier = Modifier.Companion.padding(22.dp)) {

            Text(
                text = "Log In",
                fontSize = 22.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color(0xFF1AA39A),
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.Center
            )

            Spacer(modifier = Modifier.Companion.height(18.dp))

            EmailField(value = email, onValueChange = onEmailChange)

            Spacer(modifier = Modifier.Companion.height(14.dp))

            PasswordField(value = password, onValueChange = onPasswordChange)

            Spacer(modifier = Modifier.Companion.height(18.dp))

            if (errorMsg != null) {
                Text(text = errorMsg, color = Color.Companion.Red, fontSize = 12.sp)
                Spacer(modifier = Modifier.Companion.height(10.dp))
            }

            LoginButton(onClick = onLoginClick)

            Spacer(modifier = Modifier.Companion.height(20.dp))

            ActionLinks(
                onForgotClick = onForgotClick,
                onSignUpClick = onSignUpClick
            )
        }
    }
}

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    Text(text = "Email", color = Color.Companion.Gray, fontSize = 12.sp)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Enter email...") },
        modifier = Modifier.Companion.fillMaxWidth()
    )
}

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    Text(text = "Password", color = Color.Companion.Gray, fontSize = 12.sp)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Enter password...") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.Companion.fillMaxWidth()
    )
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF4B400),
            contentColor = Color.Companion.White
        )
    ) {
        Text("LOG IN", fontWeight = FontWeight.Companion.Bold)
    }
}

@Composable
fun ActionLinks(
    onForgotClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier.Companion.fillMaxWidth(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        TextButton(onClick = onForgotClick) {
            Text(
                "FORGOT PASSWORD?",
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.Black
            )
        }

        TextButton(onClick = onSignUpClick) {
            Text("SIGNUP", fontWeight = FontWeight.Companion.Bold, color = Color.Companion.Black)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLogin() {
    GymRat20Theme {
        LoginContent(
            email = "",
            password = "",
            errorMsg = null,
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onForgotClick = {},
            onSignUpClick = {}
        )
    }
}