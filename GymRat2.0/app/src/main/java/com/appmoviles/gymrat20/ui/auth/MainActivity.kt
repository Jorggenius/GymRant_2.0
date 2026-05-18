package com.appmoviles.gymrat20.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.ui.home.HomeActivity
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.auth.WelcomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymRat20Theme {
                GymRatWelcomeScreen()
            }
        }
    }
}

@Composable
fun GymRatWelcomeScreen(
    viewModel: WelcomeViewModel = viewModel()
) {
    Scaffold(
        modifier = Modifier.Companion.fillMaxSize(),
        containerColor = Color.Companion.White
    ) { innerPadding ->
        WelcomeContent(
            modifier = Modifier.Companion.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
fun WelcomeContent(
    modifier: Modifier = Modifier.Companion,
    viewModel: WelcomeViewModel
) {
    val context = LocalContext.current

    val isLogged by viewModel.isLogged
    val fullName by viewModel.fullName

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.Companion.height(40.dp))

        WelcomeTitle()

        Spacer(modifier = Modifier.Companion.height(30.dp))

        WelcomeLogo()

        Spacer(modifier = Modifier.Companion.weight(1f))

        // Botón principal: si está logueado -> Continuar, si no -> Login
        WelcomeMainButton(
            text = if (isLogged) "CONTINUAR" else "LOG IN",
            onClick = {
                if (isLogged) {
                    // Cambia HomeActivity si tu app usa otra pantalla
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }
            }
        )

        // Botón secundario: aparece solo si está logueado
        if (isLogged) {
            Spacer(modifier = Modifier.Companion.height(12.dp))
            WelcomeSecondaryButton(
                text = "Cerrar sesión (${fullName.ifBlank { "Usuario" }})",
                onClick = { viewModel.logout() }
            )
        }

        Spacer(modifier = Modifier.Companion.height(24.dp))
    }
}

@Composable
fun WelcomeTitle() {
    Text(
        text = "Gym Rat",
        fontSize = 34.sp,
        fontWeight = FontWeight.Companion.Bold
    )
}

@Composable
fun WelcomeLogo() {
    Image(
        painter = painterResource(id = R.drawable.gym_rat_logo),
        contentDescription = "Gym Rat Logo",
        modifier = Modifier.Companion
            .height(220.dp)
            .fillMaxWidth()
    )
}

@Composable
fun WelcomeMainButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF009688),
            contentColor = Color.Companion.White
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Companion.Bold
        )
    }
}

@Composable
fun WelcomeSecondaryButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.Companion.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcome() {
    GymRat20Theme {
        // Preview sin ViewModel real (para que no falle)
        Scaffold(containerColor = Color.Companion.White) { innerPadding ->
            Column(
                modifier = Modifier.Companion
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.Companion.height(40.dp))
                WelcomeTitle()
                Spacer(modifier = Modifier.Companion.height(30.dp))
                WelcomeLogo()
                Spacer(modifier = Modifier.Companion.weight(1f))
                WelcomeMainButton(text = "LOG IN", onClick = {})
                Spacer(modifier = Modifier.Companion.height(24.dp))
            }
        }
    }
}