package com.appmoviles.gymrat20.viewmodel.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appmoviles.gymrat20.R

class LegsViewModel : ViewModel() {

    private val _options = mutableStateOf(
        listOf(
            TrainingOption("PIERNAS: HIPERTROFIA", R.drawable.piernas_hipertrofia),
            TrainingOption("PIERNAS: RESISTENCIA", R.drawable.piernas_resistencia),
            TrainingOption("PIERNAS: FUNCIONAL", R.drawable.piernas_funcional),
        )
    )
    val options: State<List<TrainingOption>> = _options

    private val _navigateTo = mutableStateOf<ScreenNav?>(null)
    val navigateTo: State<ScreenNav?> = _navigateTo

    fun onBackClick() { _navigateTo.value = ScreenNav.Back }
    fun onOptionClick(title: String) { _navigateTo.value = ScreenNav.Option(title) }
    fun consumeNavigation() { _navigateTo.value = null }
}