package com.appmoviles.gymrat20.ui.exercises.back

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.components.EjerciciosContent
import com.appmoviles.gymrat20.ui.detail.DetalleActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.exercises.back.BackResisViewModel
import java.text.Normalizer

class BackResisActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: BackResisViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Espalda Resistencia",
                    imagenRes = R.drawable.espalda_resistencia,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "remo con banda elastica" -> R.raw.backresis_remo_banda_elastica
                            "face pull con banda" -> R.raw.backresis_face_pull_banda
                            "remo invertido asistido" -> R.raw.backresis_remo_invertido_asistido
                            "pajaros inclinados con mancuernas" -> R.raw.backresis_pajaros_inclinados_mancuernas
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