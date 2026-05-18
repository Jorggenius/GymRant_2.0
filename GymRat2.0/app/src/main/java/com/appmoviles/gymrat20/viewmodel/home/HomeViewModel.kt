package com.appmoviles.gymrat20.viewmodel.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.appmoviles.gymrat20.R
import com.appmoviles.gymrat20.UserStorage
import com.appmoviles.gymrat20.ui.home.CategoryItem

sealed class HomeNav {
    data object GoToSignUp : HomeNav()
    data object GoToSettings : HomeNav()
    data class GoToCategory(val title: String) : HomeNav()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val storage = UserStorage()
    private val context = application.applicationContext

    private val _fullName = mutableStateOf("")
    val fullName: State<String> = _fullName

    private val _categories = mutableStateOf(
        listOf(
            CategoryItem("ESPALDA", R.drawable.ic_espalda),
            CategoryItem("PECHO", R.drawable.ic_pecho),
            CategoryItem("PIERNAS", R.drawable.ic_pierna),
            CategoryItem("CARDIO", R.drawable.ic_cardio),
        )
    )
    val categories: State<List<CategoryItem>> = _categories

    private val _navigateTo = mutableStateOf<HomeNav?>(null)
    val navigateTo: State<HomeNav?> = _navigateTo

    init {
        // ✅ ahora viene desde tu clase Usuario
        _fullName.value = storage.getUser(context)?.nombre.orEmpty()
    }

    fun onSettingsClick() {
        _navigateTo.value = HomeNav.GoToSettings
    }

    fun onCategoryClick(title: String) {
        _navigateTo.value = HomeNav.GoToCategory(title)
    }

    fun logout() {
        storage.logout(context)
        _navigateTo.value = HomeNav.GoToSignUp
    }

    fun consumeNavigation() {
        _navigateTo.value = null
    }

    // ✅ Útil si vuelves de Settings y quieres refrescar el nombre
    fun refreshUser() {
        _fullName.value = storage.getUser(context)?.nombre.orEmpty()
    }
}