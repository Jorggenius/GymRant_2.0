package com.appmoviles.gymrat20.ui.exercises.chest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.components.EjerciciosContent
import com.appmoviles.gymrat20.ui.detail.DetalleActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.exercises.chest.ChestHiperViewModel
import java.text.Normalizer

class ChestHiperActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: ChestHiperViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Pecho Hipertrofia",
                    imagenRes = R.drawable.pecho_hipertrofia,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "press de banca con barra" -> R.raw.pechohiper_press_de_banca_con_barra
                            "press inclinado con mancuernas" -> R.raw.pechohiper_press_inclinado_con_mancuernas
                            "aperturas con mancuernas en banco plano" -> R.raw.pechohiper_aperturas_con_mancuernas_en_banco_plano
                            "press en maquina convergente" -> R.raw.pechohiper_press_en_maquina_convergente
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