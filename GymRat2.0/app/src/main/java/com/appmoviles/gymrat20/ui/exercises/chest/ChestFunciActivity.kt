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
import com.appmoviles.gymrat20.viewmodel.exercises.chest.ChestFunciViewModel
import java.text.Normalizer

class ChestFunciActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: ChestFunciViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Pecho Funcional",
                    imagenRes = R.drawable.pecho_funcional,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "flexiones con toque de hombro" -> R.raw.pechofunci_flexiones_con_toque_de_hombro
                            "push-up con apoyo alterno" -> R.raw.pechofunci_push_up_con_apoyo_alterno
                            "press unilateral con mancuerna de pie" -> R.raw.pechofunci_press_unilateral_con_mancuerna_de_pie
                            "fondos asistidos en banco" -> R.raw.pechofunci_fondos_asistidos_en_banco
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