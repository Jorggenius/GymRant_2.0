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
import com.appmoviles.gymrat20.ui.exercises.legs.LegsFunciActivity
import com.appmoviles.gymrat20.ui.exercises.legs.LegsHiperActivity
import com.appmoviles.gymrat20.ui.exercises.legs.LegsResisActivity
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.category.LegsViewModel
import com.appmoviles.gymrat20.viewmodel.category.ScreenNav
import com.appmoviles.gymrat20.viewmodel.category.TrainingOption

class LegsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GymRat20Theme { LegsScreen() } }
    }
}

@Composable
fun LegsScreen(vm: LegsViewModel = viewModel()) {
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
                    "PIERNAS: HIPERTROFIA" -> {
                        context.startActivity(
                            Intent(context, LegsHiperActivity::class.java)
                        )
                    }

                    "PIERNAS: RESISTENCIA" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, LegsResisActivity::class.java)
                        )
                    }

                    "PIERNAS: FUNCIONAL" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, LegsFunciActivity::class.java)
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
fun LegsContentPreview() {
    GymRat20Theme {
        ChestContent(
            options = listOf(
                TrainingOption(
                    title = "PIERNAS: HIPERTROFIA",
                    imageRes = R.drawable.piernas_hipertrofia
                ),
                TrainingOption(
                    title = "PIERNAS: RESISTENCIA",
                    imageRes = R.drawable.piernas_resistencia
                ),
                TrainingOption(
                    title = "PIERNAS: FUNCIONAL",
                    imageRes = R.drawable.piernas_funcional
                )
            ),
            onBackClick = {},
            onOptionClick = {}
        )
    }
}