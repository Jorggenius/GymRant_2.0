package com.appmoviles.gymrat20.ui.exercises.back

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.components.EjerciciosContent
import com.appmoviles.gymrat20.ui.detail.DetalleActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.exercises.back.BackHiperViewModel
import java.text.Normalizer

class BackHiperActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: BackHiperViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Espalda Hipertrofia",
                    imagenRes = R.drawable.espalda_hipertrofia,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "remo con barra" -> R.raw.backhiper_remo_con_barra
                            "jalon al pecho en polea" -> R.raw.backhiper_jalon_al_pecho_en_polea
                            "remo sentado en polea" -> R.raw.backhiper_remo_sentado_en_polea
                            "pullover en polea alta" -> R.raw.backhiper_pull_over_en_polea
                            else -> 0
                        }

                        if (videoResId != 0) {
                            val intent = Intent(this, DetalleActivity::class.java).apply {
                                putExtra(DetalleActivity.Companion.EXTRA_VIDEO_RES_ID, videoResId)
                            }
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "No se encontró video para: ${ejercicio.nombre}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }
    }

    private fun normalizarTexto(texto: String): String {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            .lowercase()
            .replace("\\s+".toRegex(), " ")
            .trim()
    }
}