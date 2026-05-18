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
import com.appmoviles.gymrat20.viewmodel.exercises.chest.ChestResisViewModel
import java.text.Normalizer

class ChestResisActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: ChestResisViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Pecho Resistencia",
                    imagenRes = R.drawable.pecho_resistencia,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "flexiones de pecho" -> R.raw.pechoresis_flexiones_de_pecho
                            "flexiones inclinadas" -> R.raw.pechoresis_flexiones_inclinadas
                            "press de pecho con banda elastica" -> R.raw.pechoresis_press_de_pecho_con_banda_elastica
                            "press de pecho con mancuernas ligeras" -> R.raw.pechoresis_press_de_pecho_con_mancuernas_ligeras
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