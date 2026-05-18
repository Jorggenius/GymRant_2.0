package com.appmoviles.gymrat20.viewmodel.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.viewmodel.category.ScreenNav
import com.appmoviles.gymrat20.viewmodel.category.TrainingOption

class BackViewModel : ViewModel() {

    private val _options = mutableStateOf(
        listOf(
            TrainingOption("ESPALDA: HIPERTROFIA", R.drawable.espalda_hipertrofia),
            TrainingOption("ESPALDA: RESISTENCIA", R.drawable.espalda_resistencia),
            TrainingOption("ESPALDA: FUNCIONAL", R.drawable.espalda_funcional),
        )
    )
    val options: State<List<TrainingOption>> = _options

    private val _navigateTo = mutableStateOf<ScreenNav?>(null)
    val navigateTo: State<ScreenNav?> = _navigateTo

    fun onBackClick() { _navigateTo.value = ScreenNav.Back }
    fun onOptionClick(title: String) { _navigateTo.value = ScreenNav.Option(title) }
    fun consumeNavigation() { _navigateTo.value = null }
}