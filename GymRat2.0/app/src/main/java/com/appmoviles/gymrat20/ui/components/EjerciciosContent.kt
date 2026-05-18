package com.appmoviles.gymrat20.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.appmoviles.gymrat20.clases.Ejercicio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjerciciosContent(
    titulo: String,
    imagenRes: Int,
    ejercicios: List<Ejercicio>,
    loading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    onBack: () -> Unit,
    onEjercicioClick: (Ejercicio) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Companion.Black,
                            contentColor = Color.Companion.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                        Spacer(modifier = Modifier.Companion.width(8.dp))
                        Text("BACK")
                    }
                }
            }
        }
    ) { padding ->

        when {
            loading -> {
                Box(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Column(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.Companion.height(12.dp))

                    Button(onClick = onRetry) {
                        Text("Reintentar")
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Card(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.Companion
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Companion.Center
                        ) {
                            Image(
                                painter = painterResource(id = imagenRes),
                                contentDescription = titulo,
                                modifier = Modifier.Companion
                                    .fillMaxWidth()
                                    .height(210.dp),
                                contentScale = ContentScale.Companion.Fit
                            )
                        }
                    }

                    Spacer(modifier = Modifier.Companion.height(12.dp))

                    LazyColumn(
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(ejercicios) { ejercicio ->
                            Card(
                                modifier = Modifier.Companion
                                    .fillMaxWidth()
                                    .clickable {
                                        onEjercicioClick(ejercicio)
                                    },
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF1F1F1)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                            ) {
                                Column(
                                    modifier = Modifier.Companion.padding(16.dp)
                                ) {
                                    Text(
                                        text = ejercicio.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = "Músculo principal: ${ejercicio.musculoPrincipal}",
                                        modifier = Modifier.Companion.padding(top = 6.dp)
                                    )

                                    Text(
                                        text = "Nivel: ${ejercicio.nivel}",
                                        modifier = Modifier.Companion.padding(top = 4.dp)
                                    )

                                    Text(
                                        text = "Series: ${ejercicio.seriesRecomendadas}",
                                        modifier = Modifier.Companion.padding(top = 4.dp)
                                    )

                                    Text(
                                        text = "Repeticiones: ${ejercicio.repeticionesRecomendadas}",
                                        modifier = Modifier.Companion.padding(top = 4.dp)
                                    )

                                    Text(
                                        text = ejercicio.descripcion,
                                        modifier = Modifier.Companion.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}