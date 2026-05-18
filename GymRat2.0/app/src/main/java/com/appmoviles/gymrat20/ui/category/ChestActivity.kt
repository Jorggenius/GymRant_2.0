package com.appmoviles.gymrat20.ui.category

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.ui.exercises.chest.ChestFunciActivity
import com.appmoviles.gymrat20.ui.exercises.chest.ChestHiperActivity
import com.appmoviles.gymrat20.ui.exercises.chest.ChestResisActivity
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.category.ChestViewModel
import com.appmoviles.gymrat20.viewmodel.category.ScreenNav
import com.appmoviles.gymrat20.viewmodel.category.TrainingOption

class ChestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GymRat20Theme { ChestScreen() } }
    }
}

@Composable
fun ChestScreen(vm: ChestViewModel = viewModel()) {
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
                    "PECHO: HIPERTROFIA" -> {
                        context.startActivity(
                            Intent(context, ChestHiperActivity::class.java)
                        )
                    }

                    "PECHO: RESISTENCIA" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, ChestResisActivity::class.java)
                        )
                    }

                    "PECHO: FUNCIONAL" -> {
                        // futura navegación
                        context.startActivity(
                            Intent(context, ChestFunciActivity::class.java)
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

@Composable
fun ChestContent(
    options: List<TrainingOption>,
    onBackClick: () -> Unit,
    onOptionClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color.Companion.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.Companion.height(80.dp))

        options.forEach { opt ->
            OptionImageCard(option = opt, onClick = { onOptionClick(opt.title) })
            Spacer(modifier = Modifier.Companion.height(18.dp))
        }
        Spacer(modifier = Modifier.Companion.height(80.dp))
        BackButton(onClick = onBackClick)
        Spacer(modifier = Modifier.Companion.height(16.dp))
    }
}

@Composable
fun OptionImageCard(option: TrainingOption, onClick: () -> Unit) {
    Card(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(170.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Companion.Center,
            modifier = Modifier.Companion.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = option.imageRes),
                contentDescription = option.title,
                modifier = Modifier.Companion.fillMaxSize()
            )
        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Companion.Black,
            contentColor = Color.Companion.White
        )
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        Spacer(modifier = Modifier.Companion.width(8.dp))
        Text("BACK")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChestContentPreview() {
    GymRat20Theme {
        ChestContent(
            options = listOf(
                TrainingOption(
                    title = "PECHO: HIPERTROFIA",
                    imageRes = R.drawable.pecho_hipertrofia
                ),
                TrainingOption(
                    title = "PECHO: RESISTENCIA",
                    imageRes = R.drawable.pecho_resistencia
                ),
                TrainingOption(
                    title = "PECHO: FUNCIONAL",
                    imageRes = R.drawable.pecho_funcional
                )
            ),
            onBackClick = {},
            onOptionClick = {}
        )
    }
}