package com.appmoviles.gymrat20.viewmodel.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

sealed class AdminOneNav {
    data object Ejercicios : AdminOneNav()
    data object Usuario : AdminOneNav()
    data object Logout : AdminOneNav()
}

class AdminOneViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateTo = mutableStateOf<AdminOneNav?>(null)
    val navigateTo: State<AdminOneNav?> = _navigateTo

    fun goToEjercicios() {
        _navigateTo.value = AdminOneNav.Ejercicios
    }

    fun goToUsuario() {
        _navigateTo.value = AdminOneNav.Usuario
    }

    fun logout() {
        _navigateTo.value = AdminOneNav.Logout
    }

    fun consumeNavigation() {
        _navigateTo.value = null
    }
}