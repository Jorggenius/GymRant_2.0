package com.appmoviles.gymrat20.viewmodel.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appmoviles.gymrat20.R

data class TrainingOption(val title: String, val imageRes: Int)

sealed class ScreenNav {
    data object Back : ScreenNav()
    data class Option(val title: String) : ScreenNav()
}

class ChestViewModel : ViewModel() {

    private val _options = mutableStateOf(
        listOf(
            TrainingOption("PECHO: HIPERTROFIA", R.drawable.pecho_hipertrofia),
            TrainingOption("PECHO: RESISTENCIA", R.drawable.pecho_resistencia),
            TrainingOption("PECHO: FUNCIONAL", R.drawable.pecho_funcional),
        )
    )
    val options: State<List<TrainingOption>> = _options

    private val _navigateTo = mutableStateOf<ScreenNav?>(null)
    val navigateTo: State<ScreenNav?> = _navigateTo

    fun onBackClick() {
        _navigateTo.value = ScreenNav.Back
    }

    fun onOptionClick(title: String) {
        // Por ahora solo dejamos el evento listo (si luego quieres ir a otra Activity)
        _navigateTo.value = ScreenNav.Option(title)
    }

    fun consumeNavigation() {
        _navigateTo.value = null
    }
}