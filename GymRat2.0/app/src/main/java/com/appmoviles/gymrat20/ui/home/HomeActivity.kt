package com.appmoviles.gymrat20.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.ui.auth.SignUpActivity
import com.appmoviles.gymrat20.ui.category.BackActivity
import com.appmoviles.gymrat20.ui.category.CardioActivity
import com.appmoviles.gymrat20.ui.category.ChestActivity
import com.appmoviles.gymrat20.ui.category.LegsActivity
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme
import com.appmoviles.gymrat20.viewmodel.home.HomeNav
import com.appmoviles.gymrat20.viewmodel.home.HomeViewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymRat20Theme {
                HomeScreen()
            }
        }
    }
}

data class CategoryItem(
    val title: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: HomeViewModel = viewModel()) {
    val context = LocalContext.current

    val categories by vm.categories
    val fullName by vm.fullName
    val nav by vm.navigateTo

    LaunchedEffect(nav) {
        when (val n = nav) {
            HomeNav.GoToSignUp -> {
                val intent = Intent(context, SignUpActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                vm.consumeNavigation()
            }

            HomeNav.GoToSettings -> {
                // Aquí luego conectas tu SettingsActivity
                // context.startActivity(Intent(context, SettingsActivity::class.java))
                vm.consumeNavigation()
            }

            is HomeNav.GoToCategory -> {
                // Aquí luego conectas pantallas por categoría
                // Ej: if (n.title == "ESPALDA") ...
                val next = when (n.title) {
                    "ESPALDA" -> BackActivity::class.java
                    "PECHO" -> ChestActivity::class.java
                    "PIERNAS" -> LegsActivity::class.java
                    "CARDIO" -> CardioActivity::class.java
                    else -> null
                }

                if (next != null) {
                    context.startActivity(Intent(context, next))
                }
                vm.consumeNavigation()
            }

            null -> Unit
        }
    }

    HomeContent(
        categories = categories,
        fullName = fullName,
        onSettingsClick = vm::onSettingsClick,
        onCategoryClick = vm::onCategoryClick,
        onLogoutClick = vm::logout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    categories: List<CategoryItem>,
    fullName: String,
    onSettingsClick: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = { HomeTopBar(onSettingsClick = onSettingsClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.Companion
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.Companion.height(8.dp))

            HomeLogo()

            if (fullName.isNotBlank()) {
                Spacer(modifier = Modifier.Companion.height(6.dp))
                HomeGreeting(fullName = fullName)
            }

            Spacer(modifier = Modifier.Companion.height(12.dp))

            CategoryGrid(
                categories = categories,
                onCategoryClick = onCategoryClick,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .weight(1f)
            )

            HomeLogoutButton(onLogoutClick = onLogoutClick)

            Spacer(modifier = Modifier.Companion.height(18.dp))
        }
    }
}

@Composable
fun HomeTopBar(onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    }
}

@Composable
fun HomeLogo() {
    Image(
        painter = painterResource(id = R.drawable.gym_rat_logo),
        contentDescription = "Gym Rat Logo",
        modifier = Modifier.Companion.height(140.dp)
    )
}

@Composable
fun HomeGreeting(fullName: String) {
    Text(
        text = "Hola, $fullName",
        fontWeight = FontWeight.Companion.Bold,
        modifier = Modifier.Companion.fillMaxWidth(),
        textAlign = TextAlign.Companion.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryGrid(
    categories: List<CategoryItem>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(categories) { item ->
            CategoryCard(
                item = item,
                onClick = { onCategoryClick(item.title) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    item: CategoryItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier.Companion.size(90.dp)
            )
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Text(item.title, fontWeight = FontWeight.Companion.Bold)
        }
    }
}

@Composable
fun HomeLogoutButton(onLogoutClick: () -> Unit) {
    Button(
        onClick = onLogoutClick,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Companion.Black,
            contentColor = Color.Companion.White
        )
    ) {
        Text("ABANDONAR", fontWeight = FontWeight.Companion.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Home Screen Preview")
@Composable
fun PreviewHomeScreen() {
    GymRat20Theme {
        HomeContent(
            categories = listOf(
                CategoryItem("ESPALDA", R.drawable.ic_espalda),
                CategoryItem("PECHO", R.drawable.ic_pecho),
                CategoryItem("PIERNAS", R.drawable.ic_pierna),
                CategoryItem("CARDIO", R.drawable.ic_cardio),
            ),
            fullName = "Jorge",
            onSettingsClick = {},
            onCategoryClick = {},
            onLogoutClick = {}
        )
    }
}