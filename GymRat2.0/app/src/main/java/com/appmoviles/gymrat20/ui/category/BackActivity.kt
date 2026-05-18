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
import com.appmoviles.gymrat20.ui.exercises.back.BackFunciActivity
import com.appmoviles.gymrat20.ui.exercises.back.BackHiperActivity
import com.appmoviles.gymrat20.ui.exercises.back.BackResisActivity
import com.appmoviles.gymrat20.ui.category.ChestContent
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.category.BackViewModel
import com.appmoviles.gymrat20.viewmodel.category.ScreenNav
import com.appmoviles.gymrat20.viewmodel.category.TrainingOption

class BackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymRat20Theme {
                BackScreen()
            }
        }
    }
}

@Composable
fun BackScreen(vm: BackViewModel = viewModel()) {
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
                    "ESPALDA: HIPERTROFIA" -> {
                        context.startActivity(
                            Intent(context, BackHiperActivity::class.java)
                        )
                    }

                    "ESPALDA: RESISTENCIA" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, BackResisActivity::class.java)
                        )
                    }

                    "ESPALDA: FUNCIONAL" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, BackFunciActivity::class.java)
                        )
                    }
                }
                vm.consumeNavigation()
            }

            null -> Unit
        }
    }

    ChestContent( // reutilizamos el mismo layout
        options = options,
        onBackClick = vm::onBackClick,
        onOptionClick = vm::onOptionClick
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BackContentPreview() {
    GymRat20Theme {
        ChestContent(
            options = listOf(
                TrainingOption(
                    title = "ESPALDA: HIPERTROFIA",
                    imageRes = R.drawable.espalda_hipertrofia
                ),
                TrainingOption(
                    title = "ESPALDA: RESISTENCIA",
                    imageRes = R.drawable.espalda_resistencia
                ),
                TrainingOption(
                    title = "ESPALDA: FUNCIONAL",
                    imageRes = R.drawable.espalda_funcional
                )
            ),
            onBackClick = {},
            onOptionClick = {}
        )
    }
}