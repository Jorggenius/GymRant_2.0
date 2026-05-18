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
import com.appmoviles.gymrat20.viewmodel.exercises.cardio.CardioRopeViewModel
import java.text.Normalizer

class CardioRopeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: CardioRopeViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Cardio Cuerda",
                    imagenRes = R.drawable.cardio_lasso,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "salto basico con cuerda" -> R.raw.cardiorope_salto_basico_con_cuerda
                            "salto alterno con cuerda" -> R.raw.cardiorope_salto_alterno_con_cuerda
                            "double unders asistidos" -> R.raw.cardiorope_double_unders_asistidos
                            "salto lateral con cuerda" -> R.raw.cardiorope_salto_lateral_con_cuerda
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