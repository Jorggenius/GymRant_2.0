package com.appmoviles.gymrat20.viewmodel.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.viewmodel.category.ScreenNav
import com.appmoviles.gymrat20.viewmodel.category.TrainingOption

class CardioViewModel : ViewModel() {

    private val _options = mutableStateOf(
        listOf(
            TrainingOption("CARDIO: RUNNING", R.drawable.cardio_running),
            TrainingOption("CARDIO: BIKE", R.drawable.cardio_bike),
            TrainingOption("CARDIO: LASSO", R.drawable.cardio_lasso),
        )
    )
    val options: State<List<TrainingOption>> = _options

    private val _navigateTo = mutableStateOf<ScreenNav?>(null)
    val navigateTo: State<ScreenNav?> = _navigateTo

    fun onBackClick() { _navigateTo.value = ScreenNav.Back }
    fun onOptionClick(title: String) { _navigateTo.value = ScreenNav.Option(title) }
    fun consumeNavigation() { _navigateTo.value = null }
}