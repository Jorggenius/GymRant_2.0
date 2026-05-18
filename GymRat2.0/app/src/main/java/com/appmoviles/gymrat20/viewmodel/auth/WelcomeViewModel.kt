package com.appmoviles.gymrat20.viewmodel.auth

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.appmoviles.gymrat20.UserStorage

class WelcomeViewModel(application: Application) : AndroidViewModel(application) {

    private val userStorage = UserStorage()
    private val context = application.applicationContext

    private val _isLogged = mutableStateOf(false)
    val isLogged: State<Boolean> = _isLogged

    private val _fullName = mutableStateOf("")
    val fullName: State<String> = _fullName

    fun refreshSession() {
        _isLogged.value = userStorage.isLogged(context)
        _fullName.value = userStorage.getFullName(context).orEmpty()
    }

    fun logout() {
        userStorage.logout(context)
        refreshSession()
    }

    init {
        refreshSession()
    }
}