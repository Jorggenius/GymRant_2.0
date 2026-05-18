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
import com.appmoviles.gymrat20.ui.home.HomeActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.auth.SignUpNav
import com.appmoviles.gymrat20.viewmodel.auth.SignUpViewModel

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                GymRat20Theme {
                    SignUpScreen()
                }
        }
    }
}

@Composable
fun SignUpScreen(vm: SignUpViewModel = viewModel()) {
    val context = LocalContext.current

    val email by vm.email
    val password by vm.password
    val fullName by vm.fullName
    val errorMsg by vm.errorMsg
    val nav by vm.navigateTo

    LaunchedEffect(nav) {
        when (nav) {
            SignUpNav.Home -> {
                context.startActivity(Intent(context, HomeActivity::class.java))
                vm.consumeNavigation()
            }

            SignUpNav.Login -> {
                context.startActivity(Intent(context, LoginActivity::class.java))
                vm.consumeNavigation()
            }

            null -> Unit
        }
    }

    SignUpContent(
        email = email,
        password = password,
        fullName = fullName,
        errorMsg = errorMsg,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onFullNameChange = vm::onFullNameChange,
        onSignUpClick = vm::signUp,
        onGoToLoginClick = vm::goToLogin
    )
}

@Composable
fun SignUpContent(
    email: String,
    password: String,
    fullName: String,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onFullNameChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onGoToLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color(0xFF1AA39A)),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
            SignUpHeader()
            Spacer(modifier = Modifier.Companion.height(24.dp))

            SignUpCard(
                email = email,
                password = password,
                fullName = fullName,
                errorMsg = errorMsg,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onFullNameChange = onFullNameChange,
                onSignUpClick = onSignUpClick,
                onGoToLoginClick = onGoToLoginClick
            )
        }
    }
}

@Composable
fun SignUpHeader() {
    Text(
        text = "Gym Rat",
        fontSize = 30.sp,
        fontWeight = FontWeight.Companion.Bold,
        color = Color.Companion.White
    )
}

@Composable
fun SignUpCard(
    email: String,
    password: String,
    fullName: String,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onFullNameChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onGoToLoginClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
        modifier = Modifier.Companion
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.Companion.padding(24.dp)) {

            Text(
                text = "Sign Up",
                fontSize = 22.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color(0xFF1AA39A),
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.Center
            )

            Spacer(modifier = Modifier.Companion.height(16.dp))

            SignUpEmailField(email, onEmailChange)
            Spacer(modifier = Modifier.Companion.height(12.dp))

            SignUpPasswordField(password, onPasswordChange)
            Spacer(modifier = Modifier.Companion.height(12.dp))

            SignUpFullNameField(fullName, onFullNameChange)
            Spacer(modifier = Modifier.Companion.height(12.dp))

            if (errorMsg != null) {
                Text(text = errorMsg, color = Color.Companion.Red, fontSize = 12.sp)
                Spacer(modifier = Modifier.Companion.height(8.dp))
            }

            SignUpButton(onSignUpClick)
            Spacer(modifier = Modifier.Companion.height(16.dp))

            FooterGoToLogin(onGoToLoginClick)
        }
    }
}

@Composable
fun SignUpEmailField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        modifier = Modifier.Companion.fillMaxWidth()
    )
}

@Composable
fun SignUpPasswordField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.Companion.fillMaxWidth()
    )
}

@Composable
fun SignUpFullNameField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Full Name") },
        modifier = Modifier.Companion.fillMaxWidth()
    )
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B400))
    ) {
        Text(
            text = "SIGN UP",
            fontWeight = FontWeight.Companion.Bold,
            color = Color.Companion.White
        )
    }
}

@Composable
fun FooterGoToLogin(onGoToLoginClick: () -> Unit) {
    Column(
        modifier = Modifier.Companion.fillMaxWidth(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Text(
            text = "ALREADY HAVE AN ACCOUNT?",
            fontSize = 12.sp,
            color = Color.Companion.Gray
        )
        TextButton(onClick = onGoToLoginClick) {
            Text("LOG IN", fontWeight = FontWeight.Companion.Bold, color = Color.Companion.Black)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSignUpScreen() {
    GymRat20Theme {
        SignUpContent(
            email = "",
            password = "",
            fullName = "",
            errorMsg = null,
            onEmailChange = {},
            onPasswordChange = {},
            onFullNameChange = {},
            onSignUpClick = {},
            onGoToLoginClick = {}
        )
    }
}