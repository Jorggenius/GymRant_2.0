package com.appmoviles.gymrat20.ui.exercises.cardio

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.components.EjerciciosContent
import com.appmoviles.gymrat20.ui.detail.DetalleActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.exercises.cardio.CardioBikeViewModel
import java.text.Normalizer

class CardioBikeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: CardioBikeViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Cardio Bike",
                    imagenRes = R.drawable.cardio_bike,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "bicicleta estatica moderada" -> R.raw.cardiobike_bicicleta_estatica_moderada
                            "sprints en bicicleta" -> R.raw.cardiobike_sprints_en_bicicleta
                            "subida de resistencia en bici" -> R.raw.cardiobike_subida_de_resistencia_en_bici
                            "pedaleo rapido con baja carga" -> R.raw.cardiobike_pedaleo_rapido_con_baja_carga
                            else -> 0
                        }

                        if (videoResId != 0) {
                            val intent = Intent(this, DetalleActivity::class.java).apply {
                                putExtra(DetalleActivity.Companion.EXTRA_VIDEO_RES_ID, videoResId)
                            }
                            startActivity(intent)
                        }
                    }
                )
            }
        }
    }

    private fun normalizarTexto(texto: String): String {
        return Normalizer.normalize(texto.trim().lowercase(), Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }
}