package com.appmoviles.gymrat20.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.ui.home.AdminUsersActivity
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.auth.LoginActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.home.AdminOneNav
import com.appmoviles.gymrat20.viewmodel.home.AdminOneViewModel

class AdminOneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymRat20Theme {
                AdminOneScreen()
            }
        }
    }
}

@Composable
fun AdminOneScreen(vm: AdminOneViewModel = viewModel()) {
    val context = LocalContext.current
    val nav by vm.navigateTo

    LaunchedEffect(nav) {
        when (nav) {
            AdminOneNav.Ejercicios -> {
                context.startActivity(Intent(context, AdminExercisesActivity::class.java))
                vm.consumeNavigation()
            }

            AdminOneNav.Usuario -> {
                context.startActivity(Intent(context, AdminUsersActivity::class.java))
                vm.consumeNavigation()
            }

            AdminOneNav.Logout -> {
                context.startActivity(Intent(context, LoginActivity::class.java))
                vm.consumeNavigation()
            }

            null -> Unit
        }
    }

    AdminOneContent(
        onEjerciciosClick = vm::goToEjercicios,
        onUsuarioClick = vm::goToUsuario,
        onAbandonarClick = vm::logout
    )
}

@Composable
fun AdminOneContent(
    onEjerciciosClick: () -> Unit,
    onUsuarioClick: () -> Unit,
    onAbandonarClick: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        // Parte superior
        Column(
            modifier = Modifier.Companion.fillMaxWidth()
        ) {
            Text(
                text = "Admin",
                color = Color.Companion.Gray,
                fontWeight = FontWeight.Companion.SemiBold
            )
            Image(
                painter = painterResource(id = R.drawable.gym_rat_logo),
                contentDescription = "Logo Gym Rat",
                modifier = Modifier.Companion.size(300.dp).offset(x = 25.dp),
                contentScale = ContentScale.Companion.Fit
            )
        }

        // Parte central
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.Companion.height(40.dp))

            AdminActionButton(
                text = "EJERCICIOS",
                containerColor = Color(0xFF10B7A6),
                onClick = onEjerciciosClick
            )

            Spacer(modifier = Modifier.Companion.height(22.dp))

            AdminActionButton(
                text = "USUARIO",
                containerColor = Color(0xFF10B7A6),
                onClick = onUsuarioClick
            )
        }

        // Parte inferior
        Box(
            modifier = Modifier.Companion.fillMaxWidth(),
            contentAlignment = Alignment.Companion.Center
        ) {
            AdminActionButton(
                text = "ABANDONAR",
                containerColor = Color(0xFF111111),
                onClick = onAbandonarClick
            )
        }
    }
}

@Composable
fun AdminActionButton(
    text: String,
    containerColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.Companion
            .fillMaxWidth(0.55f)
            .height(48.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.Companion.White
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Companion.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAdminOneScreen() {
    GymRat20Theme {
        AdminOneContent(
            onEjerciciosClick = {},
            onUsuarioClick = {},
            onAbandonarClick = {}
        )
    }
}