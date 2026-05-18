package com.appmoviles.gymrat20.ui.category

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.ui.exercises.cardio.CardioBikeActivity
import com.appmoviles.gymrat20.ui.exercises.cardio.CardioRopeActivity
import com.appmoviles.gymrat20.ui.exercises.cardio.CardioRunningActivity
import com.appmoviles.gymrat20.ui.category.ChestContent
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.category.CardioViewModel
import com.appmoviles.gymrat20.viewmodel.category.ScreenNav
import com.appmoviles.gymrat20.viewmodel.category.TrainingOption

class CardioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GymRat20Theme { CardioScreen() } }
    }
}

@Composable
fun CardioScreen(vm: CardioViewModel = viewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity

    val options by vm.options
    val nav by vm.navigateTo

    LaunchedEffect(nav) {
        when (val currentNav = nav) {
            ScreenNav.Back -> {
                activity?.finish()
                vm.consumeNavigation()
            }

            is ScreenNav.Option -> {
                when (currentNav.title) {
                    "CARDIO: RUNNING" -> {
                        context.startActivity(
                            Intent(context, CardioRunningActivity::class.java)
                        )
                    }

                    "CARDIO: BIKE" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, CardioBikeActivity::class.java)
                        )
                    }

                    "CARDIO: LASSO" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, CardioRopeActivity::class.java)
                        )
                    }
                }
                vm.consumeNavigation()
            }

            null -> Unit
        }
    }

    ChestContent(
        options = options,
        onBackClick = vm::onBackClick,
        onOptionClick = vm::onOptionClick
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardioContentPreview() {
    GymRat20Theme {
        ChestContent(
            options = listOf(
                TrainingOption(
                    title = "CARDIO: RUNNING",
                    imageRes = R.drawable.cardio_running
                ),
                TrainingOption(
                    title = "CARDIO: BIKE",
                    imageRes = R.drawable.cardio_bike
                ),
                TrainingOption(
                    title = "CARDIO: LASSO",
                    imageRes = R.drawable.cardio_lasso
                )
            ),
            onBackClick = {},
            onOptionClick = {}
        )
    }
}