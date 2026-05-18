package com.appmoviles.gymrat20.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.clases.Ejercicio
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.home.AdminExercisesViewModel

class AdminExercisesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymRat20Theme {
                AdminExercisesScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun AdminExercisesScreen(
    vm: AdminExercisesViewModel = viewModel(),
    onBack: () -> Unit
) {
    val ejercicios by vm.ejercicios
    val ejercicioSeleccionado by vm.ejercicioSeleccionado
    val loading by vm.loading
    val error by vm.error
    val mensaje by vm.mensaje

    var mostrarDialogo by remember { mutableStateOf(false) }
    var modoEdicion by remember { mutableStateOf(false) }

    val categorias = listOf("pecho", "espalda", "pierna", "cardio")

    LaunchedEffect(error) {
        error?.let {
            vm.consumirError()
        }
    }

    AdminExercisesContent(
        categorias = categorias,
        ejercicios = ejercicios,
        ejercicioSeleccionado = ejercicioSeleccionado,
        loading = loading,
        error = error,
        onCategoriaClick = { categoria ->
            vm.filtrarPorCategoria(categoria)
        },
        onMostrarTodos = {
            vm.limpiarFiltro()
        },
        onEjercicioClick = { ejercicio ->
            vm.seleccionarEjercicio(ejercicio)
        },
        onAgregarClick = {
            modoEdicion = false
            mostrarDialogo = true
        },
        onEditarClick = {
            if (ejercicioSeleccionado != null) {
                modoEdicion = true
                mostrarDialogo = true
            }
        },
        onEliminarClick = {
            if (ejercicioSeleccionado != null) {
                vm.eliminarEjercicio()
            }
        },
        onBack = onBack
    )

    if (mostrarDialogo) {
        EjercicioDialog(
            ejercicioInicial = if (modoEdicion) ejercicioSeleccionado else null,
            onDismiss = { mostrarDialogo = false },
            onGuardar = { ejercicio ->
                if (modoEdicion) {
                    vm.actualizarEjercicio(ejercicio)
                } else {
                    vm.crearEjercicio(ejercicio)
                }
                mostrarDialogo = false
            }
        )
    }
}

@Composable
fun AdminExercisesContent(
    categorias: List<String>,
    ejercicios: List<Ejercicio>,
    ejercicioSeleccionado: Ejercicio?,
    loading: Boolean,
    error: String?,
    onCategoriaClick: (String) -> Unit,
    onMostrarTodos: () -> Unit,
    onEjercicioClick: (Ejercicio) -> Unit,
    onAgregarClick: () -> Unit,
    onEditarClick: () -> Unit,
    onEliminarClick: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color.Companion.White)
            .padding(20.dp)
    ) {
        Text(
            text = "Edición ejercicios",
            fontWeight = FontWeight.Companion.SemiBold,
            color = Color.Companion.DarkGray
        )

        Spacer(modifier = Modifier.Companion.height(20.dp))

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CategoriaButton("PECHO") { onCategoriaClick("pechoHiper") }
            CategoriaButton("ESPALDA") { onCategoriaClick("espaldaHiper") }
        }

        Spacer(modifier = Modifier.Companion.height(12.dp))

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CategoriaButton("PIERNA") { onCategoriaClick("legsHiper") }
            CategoriaButton("CARDIO") { onCategoriaClick("cardioRunning") }
        }

        Spacer(modifier = Modifier.Companion.height(12.dp))

        Button(
            onClick = onMostrarTodos,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Companion.LightGray)
        ) {
            Text("MOSTRAR TODOS", color = Color.Companion.Black)
        }

        Spacer(modifier = Modifier.Companion.height(20.dp))

        when {
            loading -> {
                Box(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Text(
                    text = error,
                    color = Color.Companion.Red,
                    modifier = Modifier.Companion.padding(8.dp)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.Companion.weight(1f)
                ) {
                    items(ejercicios) { ejercicio ->
                        EjercicioItem(
                            ejercicio = ejercicio,
                            seleccionado = ejercicioSeleccionado?.id == ejercicio.id,
                            onClick = { onEjercicioClick(ejercicio) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.Companion.height(16.dp))

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AccionButton("AGREGAR", Color(0xFF10B7A6), onAgregarClick)
            AccionButton("EDITAR", Color(0xFF10B7A6), onEditarClick)
        }

        Spacer(modifier = Modifier.Companion.height(12.dp))

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AccionButton("ELIMINAR", Color(0xFF10B7A6), onEliminarClick)
        }

        Spacer(modifier = Modifier.Companion.height(16.dp))

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Companion.Black),
            shape = RoundedCornerShape(6.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            Spacer(modifier = Modifier.Companion.width(6.dp))
            Text("BACK")
        }
    }
}

@Composable
fun EjercicioItem(
    ejercicio: Ejercicio,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable { onClick() }
            .background(if (seleccionado) Color(0xFFE8F8F6) else Color.Companion.Transparent)
            .padding(vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            RadioButton(
                selected = seleccionado,
                onClick = onClick
            )

            Column {
                Text(
                    text = ejercicio.nombre,
                    fontWeight = FontWeight.Companion.Bold
                )
                Text(
                    text = "${ejercicio.categoria} - ${ejercicio.nivel}",
                    color = Color.Companion.Gray
                )
            }
        }

        HorizontalDivider()
    }
}

@Composable
fun CategoriaButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B7A6)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
    ) {
        Text(text, fontWeight = FontWeight.Companion.Bold)
    }
}

@Composable
fun AccionButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
    ) {
        Text(text, fontWeight = FontWeight.Companion.Bold)
    }
}

@Composable
fun EjercicioDialog(
    ejercicioInicial: Ejercicio?,
    onDismiss: () -> Unit,
    onGuardar: (Ejercicio) -> Unit
) {
    var nombre by remember { mutableStateOf(ejercicioInicial?.nombre ?: "") }
    var categoria by remember { mutableStateOf(ejercicioInicial?.categoria ?: "") }
    var descripcion by remember { mutableStateOf(ejercicioInicial?.descripcion ?: "") }
    var musculoPrincipal by remember { mutableStateOf(ejercicioInicial?.musculoPrincipal ?: "") }
    var series by remember {
        mutableStateOf(
            ejercicioInicial?.seriesRecomendadas?.toString() ?: ""
        )
    }
    var repeticiones by remember {
        mutableStateOf(
            ejercicioInicial?.repeticionesRecomendadas?.toString() ?: ""
        )
    }
    var nivel by remember { mutableStateOf(ejercicioInicial?.nivel ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val ejercicio = Ejercicio(
                        id = ejercicioInicial?.id,
                        nombre = nombre,
                        categoria = categoria,
                        descripcion = descripcion,
                        musculoPrincipal = musculoPrincipal,
                        seriesRecomendadas = series.toIntOrNull() ?: 0,
                        repeticionesRecomendadas = repeticiones.toIntOrNull() ?: 0,
                        nivel = nivel
                    )
                    onGuardar(ejercicio)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = {
            Text(if (ejercicioInicial == null) "Agregar ejercicio" else "Editar ejercicio")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") })
                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") })
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") })
                OutlinedTextField(
                    value = musculoPrincipal,
                    onValueChange = { musculoPrincipal = it },
                    label = { Text("Músculo principal") })
                OutlinedTextField(
                    value = series,
                    onValueChange = { series = it },
                    label = { Text("Series") })
                OutlinedTextField(
                    value = repeticiones,
                    onValueChange = { repeticiones = it },
                    label = { Text("Repeticiones") })
                OutlinedTextField(
                    value = nivel,
                    onValueChange = { nivel = it },
                    label = { Text("Nivel") })
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAdminExercisesContent() {
    val ejerciciosDemo = listOf(
        Ejercicio(
            id = 1,
            nombre = "Press de banca con barra",
            categoria = "pechoHiper",
            descripcion = "Ejercicio para desarrollar el pecho",
            musculoPrincipal = "Pectorales",
            seriesRecomendadas = 4,
            repeticionesRecomendadas = 10,
            nivel = "Intermedio"
        ),
        Ejercicio(
            id = 2,
            nombre = "Remo con barra",
            categoria = "espaldaHiper",
            descripcion = "Ejercicio para fortalecer la espalda",
            musculoPrincipal = "Espalda",
            seriesRecomendadas = 4,
            repeticionesRecomendadas = 12,
            nivel = "Intermedio"
        ),
        Ejercicio(
            id = 3,
            nombre = "Sentadilla libre",
            categoria = "legsHiper",
            descripcion = "Ejercicio básico para piernas",
            musculoPrincipal = "Cuádriceps",
            seriesRecomendadas = 4,
            repeticionesRecomendadas = 8,
            nivel = "Avanzado"
        )
    )

    GymRat20Theme {
        AdminExercisesContent(
            categorias = listOf("pecho", "espalda", "pierna", "cardio"),
            ejercicios = ejerciciosDemo,
            ejercicioSeleccionado = ejerciciosDemo[0],
            loading = false,
            error = null,
            onCategoriaClick = {},
            onMostrarTodos = {},
            onEjercicioClick = {},
            onAgregarClick = {},
            onEditarClick = {},
            onEliminarClick = {},
            onBack = {}
        )
    }
}