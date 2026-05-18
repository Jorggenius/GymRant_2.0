package com.appmoviles.gymrat20.ui.exercises.legs

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.components.EjerciciosContent
import com.appmoviles.gymrat20.ui.detail.DetalleActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.exercises.legs.LegsFunciViewModel
import java.text.Normalizer

class LegsFunciActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymRat20Theme {
                val vm: LegsFunciViewModel = viewModel()

                EjerciciosContent(
                    titulo = "Pierna Funcional",
                    imagenRes = R.drawable.piernas_funcional,
                    ejercicios = vm.ejercicios,
                    loading = vm.loading,
                    error = vm.error,
                    onRetry = { vm.cargarEjercicios() },
                    onBack = { finish() },
                    onEjercicioClick = { ejercicio ->

                        val nombreNormalizado = normalizarTexto(ejercicio.nombre)

                        val videoResId = when (nombreNormalizado) {
                            "sentadilla goblet" -> R.raw.legsfunci_sentadilla_goblet
                            "step-up al banco" -> R.raw.legsfunci_step_up_al_banco
                            "lunge reverso con mancuerna" -> R.raw.legsfunci_lunge_reverso_con_mancuerna
                            "peso muerto a una pierna" -> R.raw.legsfunci_peso_muerto_a_una_pierna
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